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
    @JoinColumn(name = "material_id",referencedColumnName = "material_id",insertable = false, updatable = false)
    private Material materialId;

    @Column(name = "material_id")
    private int materialNumber;

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

    @ManyToOne(targetEntity = Company.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id", referencedColumnName = "company_id", insertable = false, updatable = false)
    private Company companyId;

    @Column(name = "company_id")
    private String companyid;

    @Column(name = "shipping_date")
    private LocalDateTime shippingDate;

    @Column(name = "purchase_order_id")
    private String purchaseOrderId;

    @Builder
    public Shipment(int shipmentId,int materialNumber, String materialName,
                    int shipmentCnt,int materialPrice,
                    Material materialId,
                    String materialMeasure,int materialCategory,
                    Company companyId,String companyid ,LocalDateTime shippingDate,
                    String manager, String purchaseOrderId
                    ){
        this.shipmentId =shipmentId;
        this.materialNumber = materialNumber;
        this.materialName = materialName;
        this.shipmentCnt = shipmentCnt;
        this.materialId = materialId;
        this.materialPrice = materialPrice;
        this.materialMeasure = materialMeasure;
        this.materialCategory = materialCategory;
        this.companyId = companyId;
        this.companyid = companyid;
        this.shippingDate = shippingDate;
        this.manager = manager;
        this.purchaseOrderId = purchaseOrderId;
    }
    public Company getCompanyId() {
        return companyId;
    }
}
