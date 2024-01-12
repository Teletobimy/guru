package com.ezen.guru.dto.receive;

import com.ezen.guru.domain.Material;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class ShipmentDetailResponse {
    private final int shipmentId;
    private final Material materialId;
    private final String materialName;
    private final int shipmentCnt;
    private final String materialMeasure;
    private final int materialPrice;
    private final int materialCategory;
    private final String manager;
    private final String companyId;
    private final String companyName;
    private final String ceo;
    private final String tel;
    private final String email;
    private final String address;
    private final LocalDateTime shippingDate;
    public final String getpuchaseOrderId;

}
