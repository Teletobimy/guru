package com.ezen.guru.repository.purchaseOrder;

import com.ezen.guru.domain.purchaseOrder.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PcorderRepository extends JpaRepository<PurchaseOrder, Long> {
}
