package com.ezen.guru.repository.purchaseOrder;

import com.ezen.guru.domain.PurchaseOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PcorderRepository extends JpaRepository<PurchaseOrderDetail, Integer> {
    public List<PurchaseOrderDetail> findByPurchaseOrderStatus(String purchaseOrderStatus); // where purchaseOrderStatus = ?
}
