package com.ezen.guru.service.purchase;

import com.ezen.guru.domain.*;
import com.ezen.guru.dto.purchase.*;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

    public Page<OrderListViewResponse> orderList(int size, int page, String keyword, int category, LocalDateTime startDate, LocalDateTime endDate);

    public List<Code> findByCodeCategory(String codeCategory);

    public List<OrderDetailViewResponse> getPurchaseOrderDetail(String id);

    public List<OrderPrintViewResponse> getPurchaseOrderPrint(String id);

    public void updateOrderDetailStatus(int orderId, int newStatus);

    public void updateOrderStatus(String id);

    public void closeOrder();

    public void forceClose(String id);

    public List<Shipment> saveToShipment(List<AddShipmentRequest> shipments);


}
