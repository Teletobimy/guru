package com.ezen.guru.domain;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "purchase_order")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Entity
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

    @OneToMany(mappedBy = "purchaseOrder")
    private List<PurchaseOrderDetail> purchaseOrderDetails;

    public void loadCompany(EntityManager entityManager) {
        if (company != null && !Hibernate.isInitialized(company)) {
            // company가 초기화되지 않았다면 초기화
            Hibernate.initialize(company);
        }
    }

    @Builder
    public PurchaseOrder(String id,
                         Document document,
                         Company company,
                         String companyId,
                         int totalprice,
                         LocalDateTime regdate,
                         int status,
                         String memo,
                         LocalDateTime deadline) {
        this.id = id;
        this.document = document;
        this.company = company;
        this.totalprice = totalprice;
        this.regdate = regdate;
        this.status = status;
        this.memo = memo;
        this.deadline = deadline;
    }
}
