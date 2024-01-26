package com.ezen.guru.service.plan;
import com.ezen.guru.domain.Bicycle;
import com.ezen.guru.dto.plan.BicycleDTO;
import com.ezen.guru.repository.plan.BicycleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;

@Service
public class BicycleServiceImpl implements BicycleService {

    private final BicycleRepository bicycleRepository;

    @Autowired
    public BicycleServiceImpl(BicycleRepository bicycleRepository) {

        this.bicycleRepository = bicycleRepository;
    }
    @Override
    public Page<BicycleDTO> getAllBicycles(String bicycleName, Pageable pageable) {
        if (bicycleName == null || bicycleName.isEmpty()) {
            return bicycleRepository.findAll(pageable).map(this::convertToDTO);
        } else {
            return bicycleRepository.findAllByBicycleNameContainingIgnoreCaseOrderByBicyclePriceAsc(bicycleName, pageable)
                    .map(this::convertToDTO);
        }
    }

    public BicycleDTO convertToDTO(Bicycle bicycle) {
        // 자전거 엔터티를 자전거 DTO로 변환
        BicycleDTO bicycleDTO = new BicycleDTO();
        // 속성을 맞게 매핑
        bicycleDTO.setBicycleId(bicycle.getBicycleId());
        bicycleDTO.setBicycleName(bicycle.getBicycleName());
        bicycleDTO.setBicycleDescription(bicycle.getBicycleDescription());
        bicycleDTO.setBicycleStock(bicycle.getBicycleStock());
        bicycleDTO.setBicyclePrice(bicycle.getBicyclePrice());
        // 필요한 경우 더 많은 매핑 추가
        return bicycleDTO;
    }
    private Bicycle convertToBicycle(BicycleDTO bicycleDTO){
        Bicycle bicycle = new Bicycle();
        bicycle.setBicycleId(bicycleDTO.getBicycleId());
        bicycle.setBicycleName(bicycleDTO.getBicycleName());
        bicycle.setBicycleDescription(bicycleDTO.getBicycleDescription());
        bicycle.setBicycleDescription(bicycle.getBicycleDescription());
        bicycle.setBicycleStock(bicycleDTO.getBicycleStock());
        bicycle.setBicyclePrice(bicycleDTO.getBicyclePrice());

        return bicycle;
    }

    @Override
    public Bicycle saveBicycle(BicycleDTO bicycleDTO) {
        Bicycle bicycle = convertToBicycle(bicycleDTO);
        return bicycleRepository.save(bicycle);
    }

    @Override
    public void deleteBicycle(int bicycleId) {
        bicycleRepository.deleteById(bicycleId);
    }



    @Override
    public Bicycle getBicycleById(int bicycleId) {

        return bicycleRepository.findById(bicycleId).orElse(null);
    }


}
