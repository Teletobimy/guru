package com.ezen.guru.repository.purchase;

import com.ezen.guru.domain.PurchaseOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<PurchaseOrderDetail, Integer> {
    public List<PurchaseOrderDetail> findByPurchaseOrder_PurchaseOrderStatus(int purchaseOrderStatus); // where purchaseOrderStatus = ?


}
