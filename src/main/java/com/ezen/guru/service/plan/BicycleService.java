package com.ezen.guru.service.plan;
import com.ezen.guru.domain.Bicycle;
import com.ezen.guru.dto.plan.BicycleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BicycleService {

    Page<BicycleDTO> getAllBicycles(String bicycleName, Pageable pageable);
    Bicycle saveBicycle(BicycleDTO bicycle);
    void deleteBicycle(int bicycleId);

    Bicycle getBicycleById(int bicycleId);


    BicycleDTO convertToDTO(Bicycle bicycleById);
}
