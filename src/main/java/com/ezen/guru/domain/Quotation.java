package com.ezen.guru.domain;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Table(name = "Quotation")
@Getter
@Setter
@Entity
public class Quotation {

    @Id
    @Column(name="quotation_id", updatable = false)
    private String id;

    @Column(name="bidding_no")
    private int biddingNo;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "company_name")
    private String company_name;

    @Column(name="quotation_totalprice")
    private int quotation_totalprice;

    @Column(name="regdate")
    private LocalDateTime regdate;

    @Column(name="deadline")
    private LocalDateTime deadline;

    @Column(name="quotation_status")
    private int status;

    @Column(name="lead_time")
    private String leadTime;

    @Column(name="trade_terms")
    private String tradeTerms;

    @Column(name="payment_terms")
    private String paymentTerms;

    @Column(name="quotation_memo")
    private String quotation_memo;

    @OneToMany(mappedBy = "quotation", cascade = CascadeType.ALL)
    private List<QuotationDetail> quotationDetails;

    @PrePersist
    public void prePersist() {
        this.regdate = LocalDateTime.now();
    }

}
