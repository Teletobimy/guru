package com.ezen.guru.service.export;

import com.ezen.guru.domain.*;
import com.ezen.guru.dto.export.ExportDTO;
import com.ezen.guru.dto.plan.ProducePlanerDTO;
import com.ezen.guru.repository.CodeRepository;
import com.ezen.guru.repository.export.ExportCustomRepository;
import com.ezen.guru.repository.export.ExportRepository;
import com.ezen.guru.repository.plan.BicycleRepository;
import com.ezen.guru.repository.plan.MaterialRepository;
import com.ezen.guru.repository.plan.ProducePlanerCustomRepositoryImpl;
import com.ezen.guru.repository.plan.ProducePlanerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExportService {

    private final ExportRepository exportRepository;
    private final ExportCustomRepository exportCustomRepository;
    private final ProducePlanerRepository producePlanerRepository;
    private final ProducePlanerCustomRepositoryImpl producePlanerCustomRepository;
    private final CodeRepository codeRepository;
    private final MaterialRepository materialRepository;
    private final BicycleRepository bicycleRepository;

    public int[] producePlanerCnt() {

        int[] array = new int[3];
        List<ProducePlaner> all = producePlanerRepository.findAll();
        int cnt = 0;
        int cnt1 = 0;
        int cnt99 = 0;
        List<String> id = new ArrayList<>();

        for (ProducePlaner entity : all) {
            //출고완료개수측정
            if (entity.getProducePlanerStatus() == 99) {
                cnt++;
            }
            //생산계획서번호별로 구분하는 코드
            if (id.isEmpty()) {
                id.add(entity.getEmbeddedId().getProducePlanerId());
                cnt1++;
                //생산계획서번호별 출고처리 카운트
                if (entity.getProducePlanerStatus() == 99) {
                    cnt99++;
                }
            } else {
                int idCnt = 0;
                for (String i : id) {

                    if (entity.getEmbeddedId().getProducePlanerId().equals(i)) {
                        idCnt++;
                        break;
                    }
                }
                if (idCnt == 0) {
                    id.add(entity.getEmbeddedId().getProducePlanerId());
                    cnt1++;
                    //생산계획서번호별 출고처리 카운트
                    if (entity.getProducePlanerStatus() == 99) {
                        cnt99++;
                    }
                }
            }
        }

        double result = ((double) cnt / all.size()) * 100;
        array[0] = (int) result; //인덱스[0]:생산계획서 마감률
        array[1] = cnt1; //인덱스[1]:생산계획서 총 개수
        array[2] = cnt1-cnt99; //인덱스[2]:(생산계획서 총 개수)-(출고완료된 생산계획서)

        return array;
    }

    public Page<ProducePlanerDTO> findAll(int size, int page, int category, String keyword, LocalDateTime startDate, LocalDateTime endDate) {

        Page<ProducePlaner> result = producePlanerCustomRepository.producePlanerList(size, page, category, keyword, startDate, endDate);
        return result.map(ProducePlanerDTO::new);
    }

    public Page<ExportDTO> findExportList(int size, int page, String keyword, LocalDateTime startDate, LocalDateTime endDate) {

        Page<Export> result = exportCustomRepository.exportList(size, page, keyword, startDate, endDate);
        return result.map(ExportDTO::new);
    }

    public ProducePlanerDTO findById(String producePlanerId, int bicycleId, int materialId) {

        ProducePlanerId id = new ProducePlanerId(producePlanerId, bicycleId, materialId);
        ProducePlaner producePlaner = producePlanerRepository.findByEmbeddedId(id);
        return new ProducePlanerDTO(producePlaner);
    }

    public List<ProducePlanerDTO> findByProducePlanerId(String producePlanerId) {

        List<ProducePlaner> list = producePlanerRepository.findByEmbeddedIdProducePlanerId(producePlanerId);

        return list.stream().map(ProducePlanerDTO::new).toList();
    }

    public List<ExportDTO> findExport(String producePlanerId) {

        List<Export> list = exportRepository.findByEmbeddedIdProducePlanerId(producePlanerId);

        return list.stream().map(ExportDTO::new).toList();
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

    public void saveProducePlaner(List<ProducePlanerDTO> dtoList) {

        System.out.println("dtoList : " + dtoList);

        List<ProducePlaner> entityList = new ArrayList<>();
        for (ProducePlanerDTO dto : dtoList) {

            ProducePlaner entity = ProducePlanerDTO.toEntity(dto);
            entityList.add(entity);
        }
        System.out.println("after--entityList : " + entityList.size());

        if (dtoList.size() == entityList.size()) {

            for (ProducePlaner entity : entityList) {

                producePlanerRepository.save(entity);
                System.out.println("---saved entity : " + entity);
            }
        }
    }

    public void deleteProducePlaner(String producePlanerId) {

        System.out.println("service delete-------------------------------");

        List<ProducePlaner> list = producePlanerRepository.findByEmbeddedIdProducePlanerId(producePlanerId);

        producePlanerRepository.deleteProducePlanerById(producePlanerId);
        System.out.println("status delete success---------------------- ");
    }

    public void updateStatus(ProducePlanerDTO dto) {

        System.out.println("service dto---------------------- : " + dto);
        ProducePlaner entity = ProducePlanerDTO.toEntity(dto);
        System.out.println("service entity---------------------- : " + entity);

        if (entity.getProducePlanerStatus() == 0) {

            entity.setProducePlanerStatus(2);
            System.out.println("update status 222222");
        } else {

            entity.setProducePlanerStatus(0);
            System.out.println("update status 000000");
        }
        producePlanerRepository.save(entity);
        System.out.println("entity save----------------");

        ProducePlaner producePlaner = producePlanerRepository.findByEmbeddedId(entity.getEmbeddedId());
        Material material = materialRepository.findByMaterialId(producePlaner.getEmbeddedId().getMaterialId());

        if (dto.getProducePlanerStatus() == 0 && producePlaner.getProducePlanerStatus() == 2) {

            material.setMaterialStock(material.getMaterialStock() - producePlaner.getProduceMaterialCnt());

        } else if (dto.getProducePlanerStatus() != 0 && producePlaner.getProducePlanerStatus() == 0){

            material.setMaterialStock(material.getMaterialStock() + producePlaner.getProduceMaterialCnt());

        }
        materialRepository.save(material);
    }

    public void deleteExport(String producePlanerId) {

        System.out.println("service delete-------------------------------");
        exportRepository.deleteByEmbeddedIdProducePlanerId(producePlanerId);
        System.out.println("delete success-------------------------------");
        List<Export> exportList = exportRepository.findByEmbeddedIdProducePlanerId(producePlanerId);
        System.out.println("exportList : " + exportList);

        if (exportList.isEmpty()) {

            List<ProducePlaner> list = producePlanerRepository.findByEmbeddedIdProducePlanerId(producePlanerId);

            int bicycleId = list.get(0).getEmbeddedId().getBicycleId();
            int bStock = list.get(0).getProduceBicycleCnt();

            Bicycle bicycle = bicycleRepository.findByBicycleId(bicycleId);
            bicycle.setBicycleStock(bicycle.getBicycleStock()-bStock);

            bicycleRepository.save(bicycle);

            System.out.println("producePlanerUpdate----------------------");
            producePlanerRepository.updateProducePlanerStatusById(producePlanerId, 2);
            System.out.println("status update success---------------------- ");
        }
    }

    @Transactional
    public void listExport(String producePlanerId) {

        List<ProducePlaner> list = producePlanerRepository.findByEmbeddedIdProducePlanerId(producePlanerId);

        //생산계획서 품목들의 불출완료처리된 품목 개수 측정
        for (ProducePlaner p : list) {
            if (p.getProducePlanerStatus() == 2) {
                Export export = Export.builder()
                        .embeddedId(p.getEmbeddedId())
                        .bicycleName(p.getBicycleName())
                        .materialName(p.getMaterialName())
                        .exportCnt(p.getProduceMaterialCnt())
                        .exportDate(LocalDateTime.now())
                        .build();
                exportRepository.save(export);
            }
        }

        List<Export> exportList = exportRepository.findByEmbeddedIdProducePlanerId(producePlanerId);

        //생산계획서id의 행과 불출처리된 행의 개수가 동일한지 검사
        if (exportList.size() == list.size()) {

            //일치하다면 해당 생산계획서의 모든 품목 출고완료처리(status = 99)
            producePlanerRepository.updateProducePlanerStatusById(producePlanerId, 99);

            int bicycleId = list.get(0).getEmbeddedId().getBicycleId();
            int bStock = list.get(0).getProduceBicycleCnt();

            Bicycle bicycle = bicycleRepository.findByBicycleId(bicycleId);
            bicycle.setBicycleStock(bicycle.getBicycleStock()+bStock);

            bicycleRepository.save(bicycle);
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
