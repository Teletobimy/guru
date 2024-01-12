package com.ezen.guru.service.purchase;

import com.ezen.guru.domain.*;
import com.ezen.guru.dto.purchase.OrderListViewResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {

    public Page<OrderListViewResponse> orderList(int size, int page, String keyword, int category);

    public List<Code> findByCodeCategory(String codeCategory);

    public List<PurchaseOrderDetail> getPurchaseOrderDetail(String id);

    public List<PurchaseOrderDetail> getPurchaseOrderPrint(String id);

    public void updateOrderDetailStatus(int orderId, int newStatus);

    public void updateOrderStatus(String id, int newStatus);

    public Shipment saveToShipment(Shipment shipment);

    public QcCheck saveToQcCheck(QcCheck qcCheck);

}
