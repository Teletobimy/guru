package com.ezen.guru.dto.purchase;

import com.ezen.guru.domain.*;
import com.ezen.guru.dto.plan.ProducePlanerDTO;
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
    Material materialId;
    String materialName;
    int shipmentCnt;
    int materialPrice;
    String materialMeasure;
    int materialCategory;
    Company companyId;
    LocalDateTime shippingDate;
    String purchaseOrderId;

    public static Shipment toEntity(final AddShipmentRequest dto) {
        return Shipment.builder()
                .materialId(dto.getMaterialId())
                .materialName(dto.getMaterialName())
                .shipmentCnt(dto.getShipmentCnt())
                .materialPrice(dto.getMaterialPrice())
                .materialMeasure(dto.getMaterialMeasure())
                .materialCategory(dto.getMaterialCategory())
                .companyId(dto.getCompanyId())
                .shippingDate(dto.getShippingDate())
                .purchaseOrderId(dto.getPurchaseOrderId())
                .build();
    }
}
