package com.ezen.guru.repository.purchase;

import com.ezen.guru.domain.PurchaseOrder;
import com.ezen.guru.domain.PurchaseOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<PurchaseOrder, Integer>,OrderCustomRepository {
    public List<PurchaseOrder> findByStatusOrderByDeadline(int status); // where purchase_order_status = ? order by purchase_order_deadline asc;

}
