package com.ezen.guru.service.receive;

import com.ezen.guru.domain.Code;
import com.ezen.guru.domain.QcCheck;
import com.ezen.guru.dto.receive.ShipmentDetailResponse;
import com.ezen.guru.dto.receive.ShipmentResponse;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ShipmentService {
    //public Page<ShipmentResponse> shipmentList(int size, int page);
    public Page<ShipmentResponse> shipmentList(int size, int page, String keyword, int category, LocalDateTime startDate, LocalDateTime endDate);
    public List<Code> findByCodeCategory(String codeCategory);
    public ShipmentDetailResponse findByShimentId(int shipmentId);

    public QcCheck addQcCheck( int shipmentId);

    public List<QcCheck> qcCheckList();

    public QcCheck qcCheck(int shipmentId);

}
