package com.ezen.guru.service.export;

import com.ezen.guru.domain.Code;
import com.ezen.guru.domain.ProducePlaner;
import com.ezen.guru.repository.CodeRepository;
import com.ezen.guru.repository.export.ExportRepository;
import com.ezen.guru.repository.plan.ProducePlanerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

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

    public List<ProducePlaner> findByStatus(int status) {

        List<ProducePlaner> list = producePlanerRepository.findByProducePlanerStatusNot(status);
        List<ProducePlaner> distinctList = list.stream()
                .filter(distinctByKey(producePlaner -> producePlaner.getId().getProducePlanerId()))
                .toList();

        System.out.println(list.size());
        System.out.println(distinctList.size());

        return distinctList;
    }

    public List<ProducePlaner> findByProducePlanerId(String producePlanerId) {

        return producePlanerRepository.findByIdProducePlanerId(producePlanerId);
    }

    public List<Code> findByCodeList(String codeCategory) {

        return codeRepository.findByCodeCategory(codeCategory);
    }

    public Code findByCode(String codeCategory, int codeNum) {

        return codeRepository.findByCodeCategoryAndCodeNum(codeCategory, codeNum);
    }
}
