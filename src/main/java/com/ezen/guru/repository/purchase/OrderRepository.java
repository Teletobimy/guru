package com.ezen.guru.repository.purchase;

import com.ezen.guru.domain.PurchaseOrder;
import com.ezen.guru.domain.PurchaseOrderDetail;
import com.ezen.guru.dto.purchase.OrderCompleteRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<PurchaseOrder, Integer>,OrderCustomRepository {
    public List<PurchaseOrder> findByStatusOrderByDeadline(int status); // where purchase_order_status = ? order by purchase_order_deadline asc;

    @Query("SELECT p FROM PurchaseOrder p WHERE p.id = :id")
    public Optional<PurchaseOrder> findById(@Param("id") String id);

}
