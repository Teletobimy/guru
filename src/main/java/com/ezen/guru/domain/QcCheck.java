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

    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material materialId;

    @Column(name = "manager")
    private String manager;

    @Column(name = "qccheck_cnt")
    private int qcCheckCnt;

    @Column(name = "process_status")
    private int processStatus;

    @Column(name = "purchase_order_id")
    private String purchaseOrderId;

    @Column(name = "qccheck_date")
    private LocalDateTime qccheckDate;

    @Builder
    public QcCheck(int qcCheckId,int returnStatus, int shipmentId,
                   Material materialId, String manager,
                   int qcCheckCnt, int processStatus,
                   String purchaseOrderId, LocalDateTime qccheckDate){
        this.qcCheckId = qcCheckId;
        this.returnStatus = returnStatus;
        this.materialId = materialId;
        this.shipmentId = shipmentId;
        this.manager = manager;
        this.qcCheckCnt = qcCheckCnt;
        this.processStatus = processStatus;
        this.purchaseOrderId = purchaseOrderId;
        this.qccheckDate = qccheckDate;
    }
}
