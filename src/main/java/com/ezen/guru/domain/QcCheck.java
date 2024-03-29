package com.ezen.guru.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.mapping.ToOne;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Data
@Table(name = "qccheck")
public class QcCheck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qccheck_id")
    private int qcCheckId;

    @Column(name = "return_status")
    private int returnStatus;

    @Column(name = "shipment_id")
    private int shipmentId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "material_id")
    private Material materialId;

    @Column(name = "manager")
    private String manager;

    @Column(name = "qccheck_cnt")
    private int qcCheckCnt;

    @Column(name = "pass_cnt")
    private int passCnt;

    @Column(name = "return_cnt")
    private int returnCnt;

    @Column(name = "process_status")
    private int processStatus;

    @Column(name = "purchase_order_id")
    private String purchaseOrderId;

    @Column(name = "qccheck_date")
    private LocalDateTime qccheckDate;

    @Column(name = "purchase_order_detail_id")
    private int purchaseOrderDetailId;

    @Builder
    public QcCheck(int qcCheckId,int returnStatus, int shipmentId,
                   Material materialId, String manager,
                   int qcCheckCnt,int passCnt,
                   int returnCnt, int processStatus,
                   String purchaseOrderId, LocalDateTime qccheckDate,
                   int purchaseOrderDetailId){
        this.qcCheckId = qcCheckId;
        this.returnStatus = returnStatus;
        this.materialId = materialId;
        this.shipmentId = shipmentId;
        this.manager = manager;
        this.qcCheckCnt = qcCheckCnt;
        this.passCnt = passCnt;
        this.returnCnt = returnCnt;
        this.processStatus = processStatus;
        this.purchaseOrderId = purchaseOrderId;
        this.qccheckDate = qccheckDate;
        this.purchaseOrderDetailId = purchaseOrderDetailId;
    }
}
