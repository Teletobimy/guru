package com.ezen.guru.dto.purchase;

import com.ezen.guru.domain.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddShipmentRequest {
    int materialNumber;
    String materialName;
    int shipmentCnt;
    int materialPrice;
    String materialMeasure;
    int materialCategory;
    String companyid;
    LocalDateTime shippingDate;
    String purchaseOrderId;
    int purchaseOrderDetailId;

    public static Shipment toEntity(AddShipmentRequest dto) {
        return Shipment.builder()
                .materialNumber(dto.getMaterialNumber())
                .materialName(dto.getMaterialName())
                .shipmentCnt(dto.getShipmentCnt())
                .materialPrice(dto.getMaterialPrice())
                .materialMeasure(dto.getMaterialMeasure())
                .materialCategory(dto.getMaterialCategory())
                .companyid(dto.getCompanyid())
                .shippingDate(dto.getShippingDate())
                .purchaseOrderId(dto.getPurchaseOrderId())
                .purchaseOrderDetailId(dto.getPurchaseOrderDetailId())
                .build();
    }
}
