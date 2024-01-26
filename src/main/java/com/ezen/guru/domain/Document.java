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

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name="document_id", updatable = false)
//    private String id;

    @Id
    @Column(name="document_id", updatable = false)
    private String id;

    public Document(){
        this.id = generateUniqueID();
    }

    private String generateUniqueID() {
        // 현재 날짜 정보를 포함한 문자열을 만듦
        String currentDate = LocalDateTime.now().toString();

        // 랜덤 숫자 생성
        Random random = new Random();
        int randomNumber = random.nextInt(10000); // 적절한 범위 설정

        // 현재 날짜와 랜덤 숫자를 조합하여 PK 생성
        return currentDate + "-" + randomNumber;
    }
    @Column(name="bidding_no")
    private int biddingNo;


    @Column(name="document_type")
    private int type;

    @Column(name = "company_id")
    private String company_id;

    @Column(name="totalprice")
    private int totalprice;

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
    private String memo;

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
    private List<DocumentDetail> documentDetails;
}
