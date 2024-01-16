package com.ezen.guru.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "material")

@NoArgsConstructor(access = AccessLevel.PUBLIC)

@Getter
@Setter
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

    @Column(name="company_name")
    private String companyName;

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
                    String companyName,
                    String materialName,
                    String materialDescription,
                    int materialStock,
                    int materialPrice,
                    String materialMeasure,
                    int materialCategory) {
        this.materialId = materialId;
        this.materialCode = materialCode;
        this.companyId = companyId;
        this.companyName = companyName;
        this.materialName = materialName;
        this.materialDescription = materialDescription;
        this.materialStock = materialStock;
        this.materialPrice = materialPrice;
        this.materialMeasure = materialMeasure;
        this.materialCategory = materialCategory;
    }
}
