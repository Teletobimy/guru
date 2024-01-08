package com.ezen.guru.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "material")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="material_id", updatable = false)
    private int materialId;

    @Column(name="material_code")
    private String materialCode;

    @Column(name="company_id")
    private String companyId;

//    @ManyToOne
//    @JoinColumn(name="company_id")
//    private Company company;

    @Column(name="material_name")
    private String materialName;

    @Column(name="material_description")
    private String materialDescription;

    @Column(name="material_stock")
    private int materialStock;

    @Column(name="material_price")
    private int materialPrice;

    @Column(name="material_measure")
    private String materialMeasure;

    @Column(name="material_category")
    private int materialCategory;

    @Builder
    public Material(int materialId,
                    String materialCode,
                    String companyId,
                    Company company,
                    String materialName,
                    String materialDescription,
                    int materialStock,
                    int materialPrice,
                    String materialMeasure,
                    int materialCategory) {
        this.materialId = materialId;
        this.materialCode = materialCode;
        this.companyId = companyId;
//        this.company = company;
        this.materialName = materialName;
        this.materialDescription = materialDescription;
        this.materialStock = materialStock;
        this.materialPrice = materialPrice;
        this.materialMeasure = materialMeasure;
        this.materialCategory = materialCategory;
    }
}
