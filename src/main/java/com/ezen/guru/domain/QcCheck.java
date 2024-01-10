package com.ezen.guru.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "material_id")
    private int materialId;

    @Column(name = "manager")
    private String manager;

    @Column(name = "qccheck_cnt")
    private int qcCheckCnt;

    @Column(name = "process_status")
    private int processStatus;

    @Builder
    public QcCheck(int qcCheckId,int returnStatus, int shipmentId,
                   int materialId, String manager,
                   int qcCheckCnt, int processStatus){
        this.qcCheckId = qcCheckId;
        this.returnStatus = returnStatus;
        this.materialId = materialId;
        this.shipmentId = shipmentId;
        this.manager = manager;
        this.qcCheckCnt = qcCheckCnt;
        this.processStatus = processStatus;
    }
}
