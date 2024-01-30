package com.ezen.guru.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "document_detail")
@NoArgsConstructor
@Getter
@Entity
@Setter
@ToString

public class DocumentDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="document_detail_id")
    private int id;



    @ManyToOne
    @JoinColumn(name = "document_id")
    private Document document;

    @OneToOne
    @JoinColumn(name = "material_id")
    private Material material;

    @Column(name="material_name")
    private String materialName;

    @Column(name="document_cnt")
    private int documentCnt;

    @Column(name="document_measure")
    private String documentMeasure;

    @Column(name="document_price")
    private int documentPrice;
}
