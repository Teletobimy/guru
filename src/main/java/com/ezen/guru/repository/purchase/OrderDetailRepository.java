package com.ezen.guru.repository.purchase;

import com.ezen.guru.domain.PurchaseOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<PurchaseOrderDetail, Integer> {
    public List<PurchaseOrderDetail> findByPurchaseOrder_id(String id); // where purchase_order_id = ?;


    @Modifying(clearAutomatically = true)
    @Query("UPDATE PurchaseOrderDetail pod " +
            "SET pod.qcCheckCnt = pod.qcCheckCnt + :qcCheckCnt " +
            "WHERE pod.id = (SELECT qc.purchaseOrderDetailId FROM QcCheck qc WHERE qc.qcCheckId = :qcCheckId)")
    int updateQcCheckCnt(@Param("qcCheckId") int qcCheckId, @Param("qcCheckCnt") int qcCheckCnt);
}
