package com.ezen.guru.dto.purchase;

import com.ezen.guru.domain.PurchaseOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCompleteRequest {
    String id;
    int newStatus;

    public static OrderCompleteRequest fromEntity(PurchaseOrder purchaseOrder) {
        return OrderCompleteRequest.builder()
                .id(purchaseOrder.getId())
                .newStatus(purchaseOrder.getStatus())
                .build();
    }

    public static PurchaseOrder toEntity(OrderCompleteRequest dto) {
        return PurchaseOrder.builder()
                .id(dto.getId())
                .status(dto.getNewStatus())
                .build();
    }
}
