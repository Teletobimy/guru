package com.ezen.guru.dto.purchase;

import com.ezen.guru.domain.PurchaseOrderDetail;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class OrderDetailViewResponse {

    private final int type;
    private final String id;
    private final String documentId;
    private final LocalDateTime regdate;
    private final LocalDateTime deadline;
    private final int status;
    private final String companyId;
    private final String companyName;
    private final int detailId;
    private final int materialId;
    private final String materialName;
    private final int category;
    private final int price;
    private final String cnt;
    private final int orderCnt;
    private final String measure;
    private final int totalprice;
    private final String memo;
    private final int materialprice;
    private final int check;

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
        this.orderCnt = detail.getPurchaseOrderCnt();
        this.measure = detail.getMaterialMeasure();
        this.totalprice = detail.getPurchaseOrder().getTotalprice();
        this.memo = detail.getPurchaseOrder().getMemo();
        this.materialprice = detail.getMaterialPrice() * detail.getPurchaseOrderCnt();
        this.check = detail.getCheck();
    }
}
