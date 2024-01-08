package com.ezen.guru.dto.receive;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@Builder
public class ShipmentResponse {
    private final int shipmentId;
    private final int materialId;
    private final String materialName;
    private final int shipmentCnt;
    private final String materialMeasure;
    private final int materialPrice;
    private final int materialCategory;
    private final String companyName;
    private final LocalDateTime shippingDate;

}
