package com.ezen.guru.dto.purchase;

import com.ezen.guru.domain.PurchaseOrderDetail;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class OrderPrintViewResponse {

    private final String id;
    private final LocalDateTime regdate;
    private final LocalDateTime deadline;
    private final String companyName;
    private final String companyId;
    private final String ceo;
    private final String tel;
    private final String materialName;
    private final int category;
    private final int price;
    private final String cnt;
    private final int materialprice;
    private final int totalprice;
    private final String memo;

    public OrderPrintViewResponse(PurchaseOrderDetail detail) {
        this.id = detail.getPurchaseOrder().getId();
        this.regdate = detail.getPurchaseOrder().getRegdate();
        this.deadline = detail.getPurchaseOrder().getDeadline();
        this.companyName = detail.getPurchaseOrder().getCompany().getCompanyName();
        this.companyId = detail.getPurchaseOrder().getCompany().getCompanyId();
        this.ceo = detail.getPurchaseOrder().getCompany().getCeo();
        this.tel = detail.getPurchaseOrder().getCompany().getTel();
        this.materialName = detail.getMaterialName();
        this.category = detail.getMaterialCategory();
        this.price = detail.getMaterialPrice();
        this.cnt = detail.getPurchaseOrderCnt() + " (" + detail.getMaterialMeasure() + ")";
        this.materialprice = detail.getMaterialPrice() * detail.getPurchaseOrderCnt();
        this.totalprice = detail.getPurchaseOrder().getTotalprice();
        this.memo = detail.getPurchaseOrder().getMemo();
    }
}
