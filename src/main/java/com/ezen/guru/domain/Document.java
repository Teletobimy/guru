package com.ezen.guru.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Table(name = "document")
@Getter
@Entity
public class Document {

    @Id
    @Column(name="document_id", updatable = false)
    private String id;

    @Column(name="bidding_no")
    private int biddingNo;

    @Column(name="document_type")
    private int type;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name="totalprice")
    private int document_totalprice;

    @Column(name="regdate")
    private LocalDateTime regdate;

    @Column(name="deadline")
    private LocalDateTime deadline;

    @Column(name="document_status")
    private int status;

    @Column(name="lead_time")
    private String leadTime;

    @Column(name="trade_terms")
    private String tradeTerms;

    @Column(name="payment_terms")
    private String paymentTerms;

    @Column(name="document_memo")
    private String document_memo;

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
    private List<DocumentDetail> documentDetails;
}
