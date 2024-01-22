package com.ezen.guru.repository.purchase;

import com.ezen.guru.domain.PurchaseOrder;
import com.ezen.guru.domain.PurchaseOrderDetail;
import com.ezen.guru.dto.purchase.OrderCompleteRequest;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<PurchaseOrder, Integer>,OrderCustomRepository {
    public List<PurchaseOrder> findByStatusOrderByDeadline(int status); // where purchase_order_status = ? order by purchase_order_deadline asc;

    @Transactional
    @Modifying
    @Query("UPDATE PurchaseOrder p SET p.status=2 WHERE p.id = :id")
    public int changeStatus(@Param("id") String id);

    @Modifying
    @Query("UPDATE PurchaseOrder p " +
            "SET p.status = 3 " +
            "WHERE p.id IN (" +
            "SELECT d.purchaseOrder.id FROM PurchaseOrderDetail d " +
            "GROUP BY d.purchaseOrder.id " +
            "HAVING SUM(d.purchaseOrderCnt) = SUM(d.qcCheckCnt))")
    public int closeOrder(@Param("id") String id);

    @Modifying
    @Query("UPDATE PurchaseOrder p SET p.status = 3 WHERE p.id = :id")
    public int forceClose(@Param("id") String id);

}
