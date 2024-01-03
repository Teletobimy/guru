package com.ezen.guru.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "purchase_order")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class PurchaseOrder {

    @Id
    @Column(name="purchase_order_id", updatable = false)
    private String purchaseOrderId;

    //private String documentId;
    //private String companyId;

    /*@ManyToOne
    @JoinColumn(name = "document_id")
    private Document document;*/

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "purchase_order_totalprice")
    private int purchaseOrderTotalprice;

    @Column(name = "purchase_order_regdate")
    private LocalDateTime purchaseOrderRegdate;

    @Column(name = "purchase_order_status")
    private int purchaseOrderStatus;

    @Column(name = "purchase_order_memo")
    private String purchaseOrderMemo;

//    @OneToMany(mappedBy="purchase_order")
//    private List<PurchaseOrderDetail> purchaseOrderDetail;

    /*@Builder
    public PurchaseOrder(String id,
                         String documentId,
                         String companyId,
                         int purchaseOrderTotalprice,
                         LocalDateTime purchaseOrderRegdate,
                         int purchaseOrderStatus,
                         String purchaseOrderMemo) {
        this.purchaseOrderId = purchaseOrderId;
        this.documentId = documentId;
        this.companyId = companyId;
        this.purchaseOrderTotalprice = purchaseOrderTotalprice;
        this.purchaseOrderRegdate = purchaseOrderRegdate;
        this.purchaseOrderStatus = purchaseOrderStatus;
        this.purchaseOrderMemo = purchaseOrderMemo;
    }*/
}
