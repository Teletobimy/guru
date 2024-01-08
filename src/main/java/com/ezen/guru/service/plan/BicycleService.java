package com.ezen.guru.service.plan;
import com.ezen.guru.dto.plan.BicycleDTO;

import java.util.List;

public interface BicycleService {
    List<BicycleDTO> getAllBicycles();
    BicycleDTO getBicycleById(int bicycleId);
    BicycleDTO createBicycle(BicycleDTO bicycleDTO);
    void deleteBicycle(int bicycleId);
}
