package com.ezen.guru.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "purchase_order")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class PurchaseOrder {

    @Id
    @Column(name="purchase_order_id", updatable = false)
    private String id;
    @Column(name="document_id")
    private String documentId;
    @Column(name="company_id")
    private String companyId;
    @Column(name="purchase_order_totalprice")
    private int totalprice;
    @Column(name="purchase_order_regdate")
    private LocalDateTime regdate;
    @Column(name="purchase_order_status")
    private int status;
    @Column(name="purchase_order_memo")
    private String purchaseOrderMemo;

    @Builder
    public PurchaseOrder(String id,
                         String documentId,
                         String companyId,
                         int totalprice,
                         LocalDateTime regdate,
                         int status,
                         String purchaseOrderMemo) {
        this.id = id;
        this.documentId = documentId;
        this.companyId = companyId;
        this.totalprice = totalprice;
        this.regdate = regdate;
        this.status = status;
        this.purchaseOrderMemo = purchaseOrderMemo;
    }
}
