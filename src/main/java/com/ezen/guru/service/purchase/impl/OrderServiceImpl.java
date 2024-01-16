package com.ezen.guru.service.purchase.impl;

import com.ezen.guru.domain.*;
import com.ezen.guru.dto.purchase.AddShipmentRequest;
import com.ezen.guru.dto.purchase.OrderListViewResponse;
import com.ezen.guru.repository.CodeRepository;
import com.ezen.guru.repository.purchase.OrderDetailRepository;
import com.ezen.guru.repository.purchase.OrderRepository;
import com.ezen.guru.repository.receive.QcCheckRepository;
import com.ezen.guru.repository.receive.ShipmentRepository;
import com.ezen.guru.service.purchase.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository detailRepository;
    private final CodeRepository codeRepository;
    private final ShipmentRepository shipmentRepository;
    private final QcCheckRepository qcCheckRepository;

    @Override
    public Page<OrderListViewResponse> orderList(int size, int page, String keyword, int category) {
        return orderRepository.orderList(size, page, keyword, category);
    }
    @Override
    public List<Code> findByCodeCategory(String codeCategory) {
        return codeRepository.findByCodeCategory(codeCategory);
    }

    @Override
    public List<PurchaseOrderDetail> getPurchaseOrderDetail(String id){
        return detailRepository.findByPurchaseOrder_id(id);
    }

    @Override
    public List<PurchaseOrderDetail> getPurchaseOrderPrint(String id){
        return detailRepository.findByPurchaseOrder_id(id);
    }
    @Override
    public void updateOrderDetailStatus(int orderId, int newStatus) {
        PurchaseOrderDetail order = detailRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("주문을 찾을 수 없습니다."));
        order.setCheck(newStatus);
        detailRepository.save(order);
    }

    @Override
    public void updateOrderStatus(String id, int newStatus) {
        PurchaseOrder order = orderRepository.findById(id);
        order.setStatus(newStatus);
        orderRepository.save(order);
    }

    @Override
    public List<Shipment> saveToShipment(List<AddShipmentRequest> shipments) {
        List<Shipment> shipmentEntities = shipments.stream()
                .map(AddShipmentRequest::toEntity) // toEntity 메서드 사용
                .collect(Collectors.toList());

        return shipmentRepository.saveAll(shipmentEntities);
    }

    @Override
    public QcCheck saveToQcCheck(QcCheck qcCheck) {
        return qcCheckRepository.save(qcCheck);
    }
}
