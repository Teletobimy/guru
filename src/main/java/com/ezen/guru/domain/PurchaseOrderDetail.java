package com.ezen.guru.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "purchase_order_detail")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@Entity
@ToString
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

    @Column(name="purchase_order_check")
    private int check;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="purchase_order_id")
    private PurchaseOrder purchaseOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="material_id")
    private Material material;

    @Column(name = "qccheck_cnt")
    private int qcCheckCnt;

    @Builder
    public PurchaseOrderDetail(int id,
                               int purchaseOrderCnt,
                               String materialName,
                               int materialCategory,
                               String materialMeasure,
                               int materialPrice,
                               int check,
                               PurchaseOrder purchaseOrder,
                               Material material,
                               int qcCheckCnt) {
        this.id = id;
        this.purchaseOrderCnt = purchaseOrderCnt;
        this.materialName = materialName;
        this.materialCategory = materialCategory;
        this.materialMeasure = materialMeasure;
        this.materialPrice = materialPrice;
        this.check = check;
        this.purchaseOrder = purchaseOrder;
        this.material = material;
        this.qcCheckCnt = qcCheckCnt;
    }
}
