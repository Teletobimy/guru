package com.ezen.guru.service.export;

import com.ezen.guru.domain.Code;
import com.ezen.guru.domain.ProducePlaner;
import com.ezen.guru.dto.plan.ProducePlanerDTO;
import com.ezen.guru.repository.CodeRepository;
import com.ezen.guru.repository.export.ExportRepository;
import com.ezen.guru.repository.plan.ProducePlanerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExportService {

    private final ExportRepository exportRepository;
    private final ProducePlanerRepository producePlanerRepository;
    private final CodeRepository codeRepository;

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

//    public List<ProducePlanerDTO> findProducePlanerList(int producePlanerStatus) {
//
//        List<ProducePlaner> list = producePlanerRepository.findByProducePlanerStatusNot(producePlanerStatus);
//
//        List<ProducePlanerDTO> producePlanerDTOList = new ArrayList<>();
//        ProducePlanerDTO producePlanerDTO;
//        int cnt = -1;
//
//        for (int i = 0; i < list.size(); i++) {
//
//            if (i == 0 || !list.get(i).getId().getProducePlanerId().equals(list.get(i - 1).getId().getProducePlanerId())) {
//
//                producePlanerDTO = new ProducePlanerDTO();
//                producePlanerDTO.setProducePlanerId(list.get(i).getId().getProducePlanerId());
//                producePlanerDTO.setBicycleId(list.get(i).getId().getBicycleId());
//                producePlanerDTO.setBicycleName(list.get(i).getBicycle().getBicycleName());
//                producePlanerDTO.setProduceBicycleCnt(list.get(i).getProduceBicycleCnt());
//                producePlanerDTO.setMaterialId(list.get(i).getId().getMaterialId());
//                producePlanerDTO.setMaterialName(list.get(i).getMaterial().getMaterialName());
//                producePlanerDTO.setProduceMaterialCnt(list.get(i).getProduceMaterialCnt());
//                producePlanerDTO.setProducePlanerDeadline(list.get(i).getProducePlanerDeadline());
//                producePlanerDTO.setProducePlanerStatus(list.get(i).getProducePlanerStatus());
//
//                producePlanerDTOList.add(producePlanerDTO);
//                cnt++;
//                System.out.println("producePlanerDTO id : " + producePlanerDTO.getProducePlanerId());
//                System.out.println("add list["+cnt+"] id : " + producePlanerDTOList.get(cnt).getProducePlanerId());
//            }
//        }
//        return producePlanerDTOList;
//    }
    public List<ProducePlaner> findProducePlanerList(int status) {

        List<ProducePlaner> list = producePlanerRepository.findByProducePlanerStatusNot(status);
        List<ProducePlaner> distinctList = list.stream()
                .filter(distinctByKey(producePlaner -> producePlaner.getId().getProducePlanerId()))
                .toList();

        System.out.println(list.size());
        System.out.println(distinctList.size());

        return distinctList;
    }

    public List<ProducePlanerDTO> findByProducePlanerId(String producePlanerId) {

        List<ProducePlaner> list = producePlanerRepository.findByIdProducePlanerId(producePlanerId);

        List<ProducePlanerDTO> producePlanerDTOList = new ArrayList<>();
        ProducePlanerDTO producePlanerDTO;

        for (int i = 0; i < list.size(); i++) {

            producePlanerDTO = new ProducePlanerDTO();

            producePlanerDTO.setProducePlanerId(list.get(i).getId().getProducePlanerId());
            producePlanerDTO.setBicycleId(list.get(i).getId().getBicycleId());
            producePlanerDTO.setBicycleName(list.get(i).getBicycle().getBicycleName());
            producePlanerDTO.setProduceBicycleCnt(list.get(i).getProduceBicycleCnt());
            producePlanerDTO.setMaterialId(list.get(i).getId().getMaterialId());
            producePlanerDTO.setMaterialName(list.get(i).getMaterial().getMaterialName());
            producePlanerDTO.setProduceMaterialCnt(list.get(i).getProduceMaterialCnt());
            producePlanerDTO.setProducePlanerDeadline(list.get(i).getProducePlanerDeadline());
            producePlanerDTO.setProducePlanerStatus(list.get(i).getProducePlanerStatus());

            producePlanerDTOList.add(producePlanerDTO);
        }
        return producePlanerDTOList;
    }

    public List<Code> findByCodeList(String codeCategory) {

        return codeRepository.findByCodeCategory(codeCategory);
    }

    public Code findByCode(String codeCategory, int codeNum) {

        return codeRepository.findByCodeNumAndCodeCategory(codeNum, codeCategory);
    }
}
