package com.ezen.guru.domain.receive.shipment;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "shipment")
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipment_id", nullable = false)
    private int shipmentId;

    @Column(name = "material_id")
    private int materialId;
    @Column(name = "material_name")
    private String materialName;
    @Column(name = "shipment_cnt")
    private int shipmentCnt;
    @Column(name = "material_price")
    private int materialPrice;
    @Column(name = "material_measure")
    private String materialMeasure;
    @Column(name = "material_category")
    private int materialCategory;
    @Column(name = "company_id", nullable = false)
    private String companyId;
    @Column(name = "shipping_date")
    private LocalDateTime shippingDate;

    @Builder
    public Shipment(int materialId, String materialName,
                    int shipmentCnt,int materialPrice,
                    String materialMeasure,int materialCategory,
                    String companyId, LocalDateTime shippingDate
                    ){
        this.materialId = materialId;
        this.materialName = materialName;
        this.shipmentCnt = shipmentCnt;
        this.materialPrice = materialPrice;
        this.materialMeasure = materialMeasure;
        this.companyId = companyId;
        this.shippingDate = shippingDate;
    }
}
