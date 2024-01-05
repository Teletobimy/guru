package com.ezen.guru.service.export;

import com.ezen.guru.domain.ProducePlaner;
import com.ezen.guru.dto.plan.ProducePlanerDTO;
import com.ezen.guru.repository.export.ExportRepository;
import com.ezen.guru.repository.plan.ProducePlanerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExportService {

    private final ExportRepository exportRepository;
    private final ProducePlanerRepository producePlanerRepository;

    public List<ProducePlanerDTO> findProducePlanerList(int producePlanerStatus) {
        List<ProducePlaner> list = producePlanerRepository.findByProducePlanerStatus(producePlanerStatus);
        List<ProducePlanerDTO> producePlanerDTOList = new ArrayList<>();
        ProducePlanerDTO producePlanerDTO = new ProducePlanerDTO();
        for (int i = 0; i < list.size(); i++) {
            producePlanerDTO.setProducePlanerId(list.get(i).getId().getProducePlanerId());
            producePlanerDTO.setBicycleId(list.get(i).getId().getBicycleId());
            producePlanerDTO.setMaterialId(list.get(i).getId().getMaterialId());
            producePlanerDTO.setProducePlanerCnt(list.get(i).getProducePlanerCnt());
            producePlanerDTO.setProducePlanerDeadline(list.get(i).getProducePlanerDeadline());
            producePlanerDTO.setProducePlanerStatus(list.get(i).getProducePlanerStatus());

            producePlanerDTOList.add(producePlanerDTO);
        }
        return producePlanerDTOList;
    }
}
