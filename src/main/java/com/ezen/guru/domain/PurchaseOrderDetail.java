package com.ezen.guru.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
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

    @ManyToOne
    @JoinColumn(name="purchase_order_id")
    private PurchaseOrder purchaseOrder;

    @ManyToOne
    @JoinColumn(name="material_id")
    private Material material;
}
