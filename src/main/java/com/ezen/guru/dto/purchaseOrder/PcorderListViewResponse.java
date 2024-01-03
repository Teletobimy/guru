package com.ezen.guru.dto.purchaseOrder;

import com.ezen.guru.domain.PurchaseOrder;
import com.ezen.guru.domain.PurchaseOrderDetail;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PcorderListViewResponse {

    private final String id;
    private final String companyName;
    private final String materialName;
    private final LocalDateTime regdate;

    public PcorderListViewResponse(PurchaseOrderDetail purchaseOrder) {
        this.id = purchaseOrder.getPurchaseOrder().getId();
        this.companyName = purchaseOrder.getPurchaseOrder().getCompany().getCompanyName();
        this.materialName = purchaseOrder.getMaterialName();
        this.regdate = purchaseOrder.getPurchaseOrder().getDocument().getRegdate();
    }

}
