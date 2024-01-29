package com.ezen.guru.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;


@Table(name = "quotation_detail")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@Entity
@ToString
@AllArgsConstructor
@Builder
public class QuotationDetail {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name="quotationdetail_id")
        private int id;

        @ManyToOne
        @JoinColumn(name = "quotation_id")
        private Quotation quotation;

        @OneToOne
        @JoinColumn(name = "material_id")
        private Material material;

        @Column(name="material_name")
        private String materialName;

        @Column(name="quotation_cnt")
        private int quotationCnt;

        @Column(name="quotation_measure")
        private String quotationMeasure;

        @Column(name="quotation_price")
        private int quotationPrice;

}
