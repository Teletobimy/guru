package com.ezen.guru.dto.purchase;

import com.ezen.guru.domain.PurchaseOrderDetail;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderPrintViewResponse {

    String id;
    LocalDateTime regdate;
    LocalDateTime deadline;
    String companyName;
    String companyId;
    String ceo;
    String materialName;
    int category;
    int price;
    String cnt;
    int materialprice;
    int totalprice;
    String memo;

    public OrderPrintViewResponse(PurchaseOrderDetail detail) {
        this.id = detail.getPurchaseOrder().getId();
        this.regdate = detail.getPurchaseOrder().getRegdate();
        this.deadline = detail.getPurchaseOrder().getDeadline();
        this.companyName = detail.getPurchaseOrder().getCompany().getCompanyName();
        this.companyId = detail.getPurchaseOrder().getCompany().getCompanyId();
        this.ceo = detail.getPurchaseOrder().getCompany().getCeo();
        this.materialName = detail.getMaterialName();
        this.category = detail.getMaterialCategory();
        this.price = detail.getMaterialPrice();
        this.cnt = detail.getPurchaseOrderCnt() + " (" + detail.getMaterialMeasure() + ")";
        this.materialprice = detail.getMaterialPrice() * detail.getPurchaseOrderCnt();
        this.totalprice = detail.getPurchaseOrder().getTotalprice();
        this.memo = detail.getPurchaseOrder().getMemo();
    }
}
