package com.ezen.guru.dto.purchase;

import com.ezen.guru.domain.PurchaseOrderDetail;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderDetailViewResponse {

    int type;
    String id;
    String documentId;
    LocalDateTime regdate;
    LocalDateTime deadline;
    int status;
    String companyId;
    String companyName;
    int detailId;
    int materialId;
    String materialName;
    int category;
    int price;
    String cnt;
    int totalprice;
    String memo;
    int materialprice;
    int check;

    public OrderDetailViewResponse(PurchaseOrderDetail detail) {
        this.type = detail.getPurchaseOrder().getDocument().getType();
        this.id = detail.getPurchaseOrder().getId();
        this.documentId = detail.getPurchaseOrder().getDocument().getId();
        this.regdate = detail.getPurchaseOrder().getRegdate();
        this.deadline = detail.getPurchaseOrder().getDeadline();
        this.status = detail.getPurchaseOrder().getStatus();
        this.companyId = detail.getPurchaseOrder().getCompany().getCompanyId();
        this.companyName = detail.getPurchaseOrder().getCompany().getCompanyName();
        this.detailId = detail.getId();
        this.materialId = detail.getMaterial().getMaterialId();
        this.materialName = detail.getMaterialName();
        this.category = detail.getMaterialCategory();
        this.price = detail.getMaterialPrice();
        this.cnt = detail.getPurchaseOrderCnt() + " (" + detail.getMaterialMeasure() + ")";
        this.totalprice = detail.getPurchaseOrder().getTotalprice();
        this.memo = detail.getPurchaseOrder().getMemo();
        this.materialprice = detail.getMaterialPrice() * detail.getPurchaseOrderCnt();
        this.check = detail.getCheck();
    }
}
