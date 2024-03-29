package com.ezen.guru.repository.purchase;

import com.ezen.guru.domain.PurchaseOrder;
import com.ezen.guru.dto.purchase.OrderMainListResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<PurchaseOrder, Integer>,OrderCustomRepository, JpaSpecificationExecutor<PurchaseOrder> {
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
    public int closeOrder();

    @Modifying
    @Query("UPDATE PurchaseOrder p SET p.status = 3 WHERE p.id = :id")
    public int forceClose(@Param("id") String id);

    Page<PurchaseOrder> findAll(Specification<PurchaseOrder> spec, Pageable pageable);

    @Query("SELECT distinct NEW com.ezen.guru.dto.purchase.OrderMainListResponse(" +
            "p.id, MIN(d.materialName), SIZE(p.purchaseOrderDetails), p.totalprice, p.deadline, s.shippingDate) " +
            "FROM PurchaseOrder p " +
            "JOIN PurchaseOrderDetail d ON d.purchaseOrder.id = p.id " +
            "JOIN Shipment s ON s.purchaseOrderId = p.id " +
            "GROUP BY p.id, p.totalprice, p.deadline, s.shippingDate " +
            "ORDER BY s.shippingDate DESC " +
            "LIMIT 5")
    public List<OrderMainListResponse> orderMainList();

    Long countBy();

    Long countByStatus(int status);
}
