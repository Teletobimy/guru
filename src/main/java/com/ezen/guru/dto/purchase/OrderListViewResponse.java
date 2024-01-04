package com.ezen.guru.dto.purchase;

import com.ezen.guru.domain.PurchaseOrderDetail;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderListViewResponse {

    private final String id;
    private final String companyName;
    private final String materialName;
    private final LocalDateTime regdate;

    public OrderListViewResponse(PurchaseOrderDetail purchaseOrder) {
        this.id = purchaseOrder.getPurchaseOrder().getId();
        this.companyName = purchaseOrder.getPurchaseOrder().getCompany().getCompanyName();
        this.materialName = purchaseOrder.getMaterialName();
        this.regdate = purchaseOrder.getPurchaseOrder().getDocument().getRegdate();
    }

}
