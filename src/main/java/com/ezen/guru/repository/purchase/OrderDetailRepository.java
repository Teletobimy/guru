package com.ezen.guru.repository.purchase;

import com.ezen.guru.domain.PurchaseOrder;
import com.ezen.guru.domain.PurchaseOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<PurchaseOrderDetail, Integer> {
    public List<PurchaseOrderDetail> findByPurchaseOrder_id(String id); // where purchase_order_id = ?;

}
