package com.ezen.guru.repository.purchaseOrder;

import com.ezen.guru.domain.PurchaseOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PcorderRepository extends JpaRepository<PurchaseOrderDetail, Integer> {
}
