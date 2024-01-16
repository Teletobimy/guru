package com.ezen.guru.domain;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Table(name = "Quotation")
@Getter
@Entity
@Setter
@ToString
@NoArgsConstructor

public class Quotation {

    @Id
    @Column(name="quotation_id", updatable = false)
    private String id;



    @Column(name = "company_id")
    private String company_id;


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

    @Column(name="quotation_memo")
    private String quotation_memo;

    @OneToMany(mappedBy = "quotation", cascade = CascadeType.ALL)
    private List<QuotationDetail> quotationDetails;

    @Builder
    public Quotation(String id, String company_id, String company_name, int quotation_totalprice,
                     LocalDateTime regdate, LocalDateTime deadline, int status, String quotation_memo,
                     List<QuotationDetail> quotationDetails) {
        this.id = id;
        this.company_id = company_id;
        this.company_name = company_name;
        this.quotation_totalprice = quotation_totalprice;
        this.regdate = (regdate != null) ? regdate : LocalDateTime.now();
        this.deadline = deadline;
        this.status = status;
        this.quotation_memo = quotation_memo;
        this.quotationDetails = (quotationDetails != null) ? quotationDetails : new ArrayList<>();
    }

}
