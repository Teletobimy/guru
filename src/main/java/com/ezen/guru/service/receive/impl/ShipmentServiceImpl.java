package com.ezen.guru.service.receive.impl;

import com.ezen.guru.domain.Code;
import com.ezen.guru.dto.receive.ShipmentDetailResponse;
import com.ezen.guru.dto.receive.ShipmentResponse;
import com.ezen.guru.repository.CodeRepository;
import com.ezen.guru.repository.receive.ShipmentRepository;
import com.ezen.guru.service.receive.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipmentServiceImpl implements ShipmentService {
    private final ShipmentRepository shipmentRepository;
    private final CodeRepository codeRepository;
    @Override
    public Page<ShipmentResponse> shipmentList(int size, int page,String keyword, int category) {
       return shipmentRepository.shipmentList(size, page,keyword,category);

    }


    @Override
    public List<Code> findByCodeCategory(String codeCategory) {

        return codeRepository.findByCodeCategory(codeCategory);
    }

    @Override
    public ShipmentDetailResponse findByShimentId(int shipmentId) {
        return shipmentRepository.findByShipmentId(shipmentId);
    }

}
