package com.ezen.guru.repository.purchase;

import com.ezen.guru.domain.PurchaseOrderDetail;
import com.ezen.guru.dto.purchase.OrderDetailViewResponse;
import com.ezen.guru.dto.purchase.OrderPrintViewResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<PurchaseOrderDetail, Integer> {

    @Query("SELECT distinct new com.ezen.guru.dto.purchase.OrderDetailViewResponse(" +
            "d.purchaseOrder.document.type, " +
            "d.purchaseOrder.id, " +
            "d.purchaseOrder.document.id, " +
            "d.purchaseOrder.regdate, " +
            "d.purchaseOrder.deadline, " +
            "d.purchaseOrder.status, " +
            "d.purchaseOrder.company.companyId, " +
            "d.purchaseOrder.company.companyName, " +
            "d.id, " +
            "d.material.materialId, " +
            "d.materialName, " +
            "d.materialCategory, " +
            "d.materialPrice, " +
            "concat(d.purchaseOrderCnt, ' (', d.materialMeasure, ')'), " +
            "d.purchaseOrderCnt, " +
            "d.qcCheckCnt, " +
            "d.materialMeasure, " +
            "d.purchaseOrder.totalprice, " +
            "d.purchaseOrder.memo, " +
            "(d.materialPrice * d.purchaseOrderCnt), " +
            "d.check) " +
            "FROM PurchaseOrderDetail d " +
            "JOIN d.purchaseOrder p " +
            "JOIN d.material m " +
            "JOIN d.purchaseOrder.document t " +
            "WHERE d.purchaseOrder.id = :id " +
            "ORDER BY d.materialName ASC")
    List<OrderDetailViewResponse> findByPurchaseOrder(@Param("id") String id); // where purchase_order_id = ?;

    @Query("SELECT distinct new com.ezen.guru.dto.purchase.OrderPrintViewResponse(" +
            "d.purchaseOrder.id, " +
            "d.purchaseOrder.regdate, " +
            "d.purchaseOrder.deadline, " +
            "d.purchaseOrder.company.companyName, " +
            "d.purchaseOrder.company.companyId, " +
            "d.purchaseOrder.company.ceo, " +
            "d.materialName, " +
            "d.materialCategory, " +
            "d.materialPrice, " +
            "concat(d.purchaseOrderCnt, ' (', d.materialMeasure, ')'), " +
            "(d.materialPrice * d.purchaseOrderCnt), " +
            "d.purchaseOrder.totalprice, " +
            "d.purchaseOrder.memo) " +
            "FROM PurchaseOrderDetail d " +
            "JOIN d.purchaseOrder p " +
            "WHERE d.purchaseOrder.id = :id " +
            "ORDER BY d.materialName ASC")
    List<OrderPrintViewResponse> getPrintPage(@Param("id") String id); // where purchase_order_id = ?;

    @Modifying(clearAutomatically = true)
    @Query("UPDATE PurchaseOrderDetail pod " +
            "SET pod.qcCheckCnt = pod.qcCheckCnt + :qcCheckCnt " +
            "WHERE pod.purchaseOrder.id = (SELECT qc.purchaseOrderId FROM QcCheck qc WHERE qc.qcCheckId = :qcCheckId)")
    int updateQcCheckCnt(@Param("qcCheckId") int qcCheckId, @Param("qcCheckCnt") int qcCheckCnt);


}
