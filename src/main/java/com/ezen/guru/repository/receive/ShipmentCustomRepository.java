package com.ezen.guru.repository.receive;

import com.ezen.guru.dto.receive.ShipmentResponse;
import org.springframework.data.domain.Page;

public interface ShipmentCustomRepository {
    Page<ShipmentResponse> shipmentList(int size, int page,String keyword, int category);
}
