package com.ezen.guru.service.plan;
import com.ezen.guru.domain.Bicycle;
import com.ezen.guru.dto.plan.BicycleDTO;
import com.ezen.guru.repository.plan.BicycleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BicycleServiceImpl implements BicycleService {

    private final BicycleRepository bicycleRepository;

    @Autowired
    public BicycleServiceImpl(BicycleRepository bicycleRepository) {
        this.bicycleRepository = bicycleRepository;
    }

    @Override
    public List<BicycleDTO> getAllBicycles() {
        return bicycleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private BicycleDTO convertToDTO(Bicycle bicycle) {
        BicycleDTO bicycleDTO = new BicycleDTO();
        BeanUtils.copyProperties(bicycle, bicycleDTO);
        return bicycleDTO;
    }

    @Override
    public BicycleDTO getBicycleById(int bicycleId) {
        return null;
    }

    @Override
    public BicycleDTO createBicycle(BicycleDTO bicycleDTO) {
        return null;
    }

    @Override
    public void deleteBicycle(int bicycleId) {

    }

}
