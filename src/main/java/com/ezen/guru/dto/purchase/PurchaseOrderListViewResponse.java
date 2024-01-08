package com.ezen.guru.dto.purchase;

import com.ezen.guru.domain.PurchaseOrder;
import com.ezen.guru.domain.PurchaseOrderDetail;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PurchaseOrderListViewResponse {

    int type; //추후 코드테이블 연결, type String이지만 편의상 임시로 int
    String id;
    LocalDateTime regdate;
    LocalDateTime deadline;
    String companyName;
    String materialName;
    int category; //추후 코드테이블 연결, type String이지만 편의상 임시로 int
    int price;
    String cnt;
    int totalprice;
    String memo;

    public PurchaseOrderListViewResponse(PurchaseOrderDetail detail) {
        this.type = detail.getPurchaseOrder().getDocument().getType();
        this.id = detail.getPurchaseOrder().getId();
        this.regdate = detail.getPurchaseOrder().getRegdate();
        this.deadline = detail.getPurchaseOrder().getDeadline();
        this.companyName = detail.getPurchaseOrder().getCompany().getCompanyName();
        this.materialName = detail.getMaterialName();
        this.category = detail.getMaterialCategory();
        this.price = detail.getMaterialPrice();
        this.cnt = detail.getPurchaseOrderCnt() + "(" + detail.getMaterialMeasure() + ")";
        this.totalprice = detail.getPurchaseOrder().getTotalprice();
        this.memo = detail.getPurchaseOrder().getMemo();
    }
}
