package com.ezen.guru.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "purchase_order_detail")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class PurchaseOrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="purchase_order_detail_id", updatable = false)
    private int id;

    //private String purchaseOrderId;

    @Column(name = "purchase_order_cnt")
    private int purchaseOrderCnt;

    //private int materialId;

    @Column(name = "material_name")
    private String materialName;

    @Column(name = "material_category")
    private int materialCategory;

    @Column(name = "material_measure")
    private String materialMeasure;

    @Column(name = "material_price")
    private int materialPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="purchase_order_id")
    private PurchaseOrder purchaseOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="material_id")
    private Material material;

    @Builder
    public PurchaseOrderDetail(int id,
                               int purchaseOrderCnt,
                               String materialName,
                               int materialCategory,
                               String materialMeasure,
                               int materialPrice,
                               PurchaseOrder purchaseOrder,
                               Material material) {
        this.id = id;
        this.purchaseOrderCnt = purchaseOrderCnt;
        this.materialName = materialName;
        this.materialCategory = materialCategory;
        this.materialMeasure = materialMeasure;
        this.materialPrice = materialPrice;
        this.purchaseOrder = purchaseOrder;
        this.material = material;
    }
}
