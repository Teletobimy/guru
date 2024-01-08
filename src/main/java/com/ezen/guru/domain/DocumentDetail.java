package com.ezen.guru.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "document_detail")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class DocumentDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="document_detail_id")
    private int id;



    @ManyToOne
    @JoinColumn(name = "document_id")
    private Document document;

    @Column(name="material_id")
    private int materialId;

    @Column(name="material_name")
    private String materialName;

    @Column(name="document_cnt")
    private int documentCnt;

    @Column(name="document_measure")
    private String documentMeasure;

    @Column(name="document_price")
    private int documentPrice;
}
