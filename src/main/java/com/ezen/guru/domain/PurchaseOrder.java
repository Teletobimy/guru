package com.ezen.guru.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "purchase_order")
@NoArgsConstructor
@Data
@AllArgsConstructor
@Entity
@ToString
public class PurchaseOrder {

    @Id
    @Column(name="purchase_order_id", updatable = false)
    private String id;

    //private String documentId;
    //private String companyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id")
    private Document document;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    private Company company;

    @Column(name = "purchase_order_totalprice")
    private int totalprice;

    @Column(name = "purchase_order_regdate")
    private LocalDateTime regdate;

    @Column(name = "purchase_order_status")
    private int status;

    @Column(name = "purchase_order_memo")
    private String memo;

    @Column(name="purchase_order_deadline")
    private LocalDateTime deadline;

    @Column(name="lead_time")
    private String leadTime;

    @Column(name="trade_terms")
    private String tradeTerms;

    @Column(name="payment_terms")
    private String paymentTerms;

    @OneToMany(mappedBy = "purchaseOrder")
    private List<PurchaseOrderDetail> purchaseOrderDetails;

    @Builder
    public PurchaseOrder(String id,
                         int totalprice,
                         LocalDateTime regdate,
                         int status,
                         String memo,
                         LocalDateTime deadline) {
        this.id = id;
        this.totalprice = totalprice;
        this.regdate = regdate;
        this.status = status;
        this.memo = memo;
        this.deadline = deadline;
    }

    public void update(int status) {
        this.status = status;
    }
}
