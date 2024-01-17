package com.ezen.guru.service.export;

import com.ezen.guru.domain.*;
import com.ezen.guru.dto.export.ExportDTO;
import com.ezen.guru.dto.plan.ProducePlanerDTO;
import com.ezen.guru.repository.CodeRepository;
import com.ezen.guru.repository.export.ExportRepository;
import com.ezen.guru.repository.plan.BicycleRepository;
import com.ezen.guru.repository.plan.MaterialRepository;
import com.ezen.guru.repository.plan.ProducePlanerCustomRepositoryImpl;
import com.ezen.guru.repository.plan.ProducePlanerRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExportService {

    private final ExportRepository exportRepository;
    private final ProducePlanerRepository producePlanerRepository;
    private final ProducePlanerCustomRepositoryImpl producePlanerCustomRepository;
    private final CodeRepository codeRepository;
    private final MaterialRepository materialRepository;
    private final BicycleRepository bicycleRepository;

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public Page<ProducePlanerDTO> findAll(int size, int page, int category, String keyword, LocalDateTime startDate, LocalDateTime endDate) {

        Page<ProducePlaner> result = producePlanerCustomRepository.producePlanerList(size, page, category, keyword, startDate, endDate);
        return result.map(ProducePlanerDTO::new);
    }

    public List<ProducePlanerDTO> findByProducePlanerId(String producePlanerId) {

        List<ProducePlaner> list = producePlanerRepository.findByIdProducePlanerId(producePlanerId);

        return list.stream().map(ProducePlanerDTO::new).toList();
    }

    public Code findByCode(String codeCategory, int codeNum) {

        return codeRepository.findByCodeCategoryAndCodeNum(codeCategory, codeNum);
    }

    public List<Code> findByCodeCategory(String category) {

        return codeRepository.findByCodeCategory(category);
    }

    public List<Code> findByCodeList(List<ProducePlanerDTO> list) {

        List<Code> codeList = new ArrayList<>();

        for (ProducePlanerDTO dto : list) {

            Code code = findByCode("produce_planer_status", dto.getProducePlanerStatus());
            codeList.add(code);
        }
        return codeList;
    }

    public List<Code> setCodeListByProducePlanerStatus(Page<ProducePlanerDTO> list) {

        List<Code> codeList = new ArrayList<>();

        for (ProducePlanerDTO dto : list) {

            List<ProducePlanerDTO> idList = findByProducePlanerId(dto.getProducePlanerId());

            int cnt0 = 0;
            int cnt2 = 0;
            int cnt99 = 0;

            for (ProducePlanerDTO idDTO : idList) {

                int status = idDTO.getProducePlanerStatus();

                if (status == 0) {
                    cnt0++;
                } else if (status == 2) {
                    cnt2++;
                } else if (status == 99) {
                    cnt99++;
                }
            }

            int size = idList.size();
            System.out.println("idList.size() : " + size + ", cnt0 : " + cnt0 + ", cnt2 : " + cnt2 + ", cnt99 : " + cnt99);
            Code code;

            if (size == cnt0) {
                code = findByCode("produce_planer_status", 0);
            } else if (size == cnt2) {
                code = findByCode("produce_planer_status", 2);
            } else if (size == cnt99) {
                code = findByCode("produce_planer_status", 99);
            } else {
                code = findByCode("produce_planer_status", 1);
            }
            System.out.println("code : " + code);
            codeList.add(code);
        }
        return codeList;
    }

    public void addExport(ExportDTO dto) {

        validate(dto);

        Export entity = ExportDTO.toEntity(dto);
        Export export = exportRepository.save(entity);

        // 저장된 Export 엔티티의 id로 ProducePlaner를 찾음
        Optional<ProducePlaner> producePlaner = producePlanerRepository.findById(export.getId());

        // ProducePlaner의 producePlanerStatus를 2로 변경
        producePlaner.ifPresent(p -> {

            p.setProducePlanerStatus(2);
            producePlanerRepository.save(p);
        });
    }

    public void deleteExport(ExportDTO dto) {

        validate(dto);

        Export entity = ExportDTO.toEntity(dto);
        // 저장된 Export 엔티티의 id로 ProducePlaner를 찾음
        ProducePlanerId id = entity.getId();
        List<ProducePlaner> list = producePlanerRepository.findByIdProducePlanerId(id.getProducePlanerId());

        exportRepository.deleteById(id);

        // ProducePlanerId의 list에 있는 모든 행의 producePlanerStatus를 0또는 2로 변경
        for (ProducePlaner p : list) {

            switch (p.getProducePlanerStatus()) {
                //생산계획서의 품목들이 출고처리 됐을 경우 불출 취소된 행은 status:0(미불출), 나머지는 2(불출완료) 처리
                case 99:
                    if (p.getId().getMaterialId() == id.getMaterialId()) {
                        p.setProducePlanerStatus(0);

                        //상품재고가 출고취소됐으므로 해당 생산계획수량만큼 상품재고에서 차감
                        int bStock = p.getProduceBicycleCnt();
                        int bicycleId = p.getId().getBicycleId();

                        Bicycle bicycle = bicycleRepository.findByBicycleId(bicycleId);
                        bicycle.setBicycleStock(bicycle.getBicycleStock()-bStock);

                        bicycleRepository.save(bicycle);
                    } else {
                        p.setProducePlanerStatus(2);
                    }
                    producePlanerRepository.save(p);

                    //출고취소 시 원자재의 재고 수량 변경
                    int mStock = p.getProduceMaterialCnt();
                    int materialId = p.getId().getMaterialId();

                    Material material = materialRepository.findByMaterialId(materialId);
                    material.setMaterialStock(material.getMaterialStock()+mStock);

                    materialRepository.save(material);
                    break;

                //출고처리되지 않은 경우 삭제된 행만 status0으로 변경
                case 2:
                    if (p.getId().equals(id)) {
                        p.setProducePlanerStatus(0);
                    }
                    producePlanerRepository.save(p);
                    break;
            }
        }
    }

    @Transactional
    public void listExport(String producePlanerId) {

        List<Export> exportList = exportRepository.findByIdProducePlanerId(producePlanerId);
        List<ProducePlaner> list = producePlanerRepository.findByIdProducePlanerId(producePlanerId);

        //생산계획서id의 행과 불출처리된 행의 개수가 동일한지 검사
        if (exportList.size() == list.size()) {

            int cnt = 0;

            //생산계획서 품목들의 불출완료처리된 품목 개수 측정
            for (ProducePlaner entity : list) {
                if (entity.getProducePlanerStatus() == 2) {
                    cnt++;
                }
            }

            //생산계획서의 불출완료처리된 품목 개수와 생산계획서 품목 수량이 일치한지 검사
            if (list.size() == cnt) {

                System.out.println("id : " + producePlanerId);
                //일치하다면 해당 생산계획서의 모든 품목 출고완료처리
                producePlanerRepository.updateProducePlanerStatusById(producePlanerId);

                //생산계획서의 원자재들 재고를 출고수량만큼 차감
                for (ProducePlaner entity : list) {

                    int materialId = entity.getId().getMaterialId();
                    int mStock = entity.getProduceMaterialCnt();

                    Material material = materialRepository.findByMaterialId(materialId);
                    material.setMaterialStock(material.getMaterialStock()-mStock);

                    materialRepository.save(material);
                }

                int bicycleId = list.get(0).getId().getBicycleId();
                int bStock = list.get(0).getProduceBicycleCnt();

                Bicycle bicycle = bicycleRepository.findByBicycleId(bicycleId);
                bicycle.setBicycleStock(bicycle.getBicycleStock()+bStock);
            }
        }
    }

    private void validate(final ExportDTO dto) {

        if (dto == null) {
            log.warn("Entity cannot be null.");
            throw new RuntimeException("Entity cannot be null.");
        }

        if (dto.getProducePlanerId() == null) {
            log.warn("ProducePlanerId cannot be null.");
            throw new RuntimeException("ProducePlanerId cannot be null.");
        }

        if (dto.getBicycleId() == 0) {
            log.warn("BicycleId cannot be 0.");
            throw new RuntimeException("BicycleId cannot be 0.");
        }

        if (dto.getMaterialId() == 0) {
            log.warn("MaterialId cannot be 0.");
            throw new RuntimeException("MaterialId cannot be 0.");
        }

        if (dto.getBicycleName() == null) {
            log.warn("BicycleName cannot be null.");
            throw new RuntimeException("BicycleName cannot be null.");
        }

        if (dto.getMaterialName() == null) {
            log.warn("MaterialName cannot be null.");
            throw new RuntimeException("MaterialName cannot be null.");
        }

        if (dto.getExportCnt() == 0) {
            log.warn("ExportCnt cannot be 0.");
            throw new RuntimeException("ExportCnt cannot be 0.");
        }
    }
}
