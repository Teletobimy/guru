package com.ezen.guru.service.receive;

import com.ezen.guru.domain.Code;
import com.ezen.guru.dto.receive.ShipmentDetailResponse;
import com.ezen.guru.dto.receive.ShipmentResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ShipmentService {
    //public Page<ShipmentResponse> shipmentList(int size, int page);
    public Page<ShipmentResponse> shipmentList(int size, int page, String keyword, int category);
    public List<Code> findByCodeCategory(String codeCategory);
    public ShipmentDetailResponse findByShimentId(int shipmentId);
}
