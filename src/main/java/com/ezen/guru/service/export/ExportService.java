package com.ezen.guru.service.export;

import com.ezen.guru.domain.Code;
import com.ezen.guru.domain.Export;
import com.ezen.guru.domain.ProducePlaner;
import com.ezen.guru.dto.export.ExportDTO;
import com.ezen.guru.dto.plan.ProducePlanerDTO;
import com.ezen.guru.repository.CodeRepository;
import com.ezen.guru.repository.export.ExportRepository;
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

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public Page<ProducePlanerDTO> findAll(int page, int size, int category, String keyword) {

        Page<ProducePlaner> result = producePlanerCustomRepository.producePlanerList(page, size, category, keyword);
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

            for (ProducePlanerDTO idDTO : idList) {

                int status = idDTO.getProducePlanerStatus();

                if (status == 0) {
                    cnt0++;
                } else if (status == 2) {
                    cnt2++;
                }
            }

            int size = idList.size();
            System.out.println("idList.size() : " + size + ", cnt0 : " + cnt0 + ", cnt2 : " + cnt2);
            Code code;

            if (size == cnt0) {
                code = findByCode("produce_planer_status", 0);
            } else if (size == cnt2) {
                code = findByCode("produce_planer_status", 2);
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
        Optional<ProducePlaner> producePlanerOptional = producePlanerRepository.findById(export.getId());

        producePlanerOptional.ifPresent(producePlaner -> {
            // ProducePlaner의 producePlanerStatus를 2로 변경
            producePlaner.setProducePlanerStatus(2);
            producePlanerRepository.save(producePlaner);
        });
    }

    public void deleteExport(ExportDTO dto) {

        validate(dto);

        Export entity = ExportDTO.toEntity(dto);
        // 저장된 Export 엔티티의 id로 ProducePlaner를 찾음
        Optional<ProducePlaner> producePlanerOptional = producePlanerRepository.findById(entity.getId());

        exportRepository.deleteById(entity.getId());

        producePlanerOptional.ifPresent(producePlaner -> {
            // ProducePlaner의 producePlanerStatus를 2로 변경
            producePlaner.setProducePlanerStatus(0);
            producePlanerRepository.save(producePlaner);
        });
    }

    @Transactional
    public void listExport(String producePlanerId) {

        List<Export> exportList = exportRepository.findByIdProducePlanerId(producePlanerId);
        List<ProducePlaner> list = producePlanerRepository.findByIdProducePlanerId(producePlanerId);

        if (exportList.size() == list.size()) {

            int cnt = 0;

            for (ProducePlaner entity : list) {

                if (entity.getProducePlanerStatus() == 2) {
                    cnt++;
                }
            }

            if (list.size() == cnt) {

                System.out.println("id : " + producePlanerId);
                producePlanerRepository.updateProducePlanerStatusById(producePlanerId);
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
