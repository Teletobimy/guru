package com.ezen.guru.domain;

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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "material_id",referencedColumnName = "material_id")
    private Material materialId;

    @Column(name = "material_name")
    private String materialName;

    @Column(name = "manager")
    private String manager;

    @Column(name = "shipment_cnt")
    private int shipmentCnt;

    @Column(name = "material_price")
    private int materialPrice;

    @Column(name = "material_measure")
    private String materialMeasure;

    @Column(name = "material_category")
    private int materialCategory;

    @ManyToOne(targetEntity = Company.class)
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    private Company companyId;

    @Column(name = "shipping_date")
    private LocalDateTime shippingDate;

    @Column(name = "purchase_order_id")
    private String purchaseOrderId;

    @Builder
    public Shipment(int shipmentId,Material materialId, String materialName,
                    int shipmentCnt,int materialPrice,
                    String materialMeasure,int materialCategory,
                    Company companyId, LocalDateTime shippingDate,
                    String manager, String purchaseOrderId
                    ){
        this.shipmentId =shipmentId;
        this.materialId = materialId;
        this.materialName = materialName;
        this.shipmentCnt = shipmentCnt;
        this.materialPrice = materialPrice;
        this.materialMeasure = materialMeasure;
        this.companyId = companyId;
        this.shippingDate = shippingDate;
        this.manager = manager;
        this.purchaseOrderId = purchaseOrderId;
    }
}
