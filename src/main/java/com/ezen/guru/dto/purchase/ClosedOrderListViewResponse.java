package com.ezen.guru.dto.purchase;

import com.ezen.guru.domain.PurchaseOrder;
import com.ezen.guru.domain.PurchaseOrderDetail;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ClosedOrderListViewResponse {

    private final String id;
    private final String companyName;
    private final String materialName;
    private final int totalprice;
    private final LocalDateTime deadline;

    public ClosedOrderListViewResponse(PurchaseOrder purchaseOrder) {
        this.id = purchaseOrder.getId();
        this.companyName = purchaseOrder.getCompany().getCompanyName();
        PurchaseOrderDetail firstDetail = purchaseOrder.getPurchaseOrderDetails().stream().findFirst().orElse(null);

        if (firstDetail != null) {
            this.materialName = firstDetail.getMaterialName();
        } else {
            this.materialName = null;
        }

        this.totalprice = purchaseOrder.getTotalprice();
        this.deadline = purchaseOrder.getDeadline();
    }
}
