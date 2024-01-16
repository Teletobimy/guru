package com.ezen.guru.service.purchase.impl;

import com.ezen.guru.domain.*;
import com.ezen.guru.dto.purchase.AddShipmentRequest;
import com.ezen.guru.dto.purchase.OrderCompleteRequest;
import com.ezen.guru.dto.purchase.OrderDetailViewResponse;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.ezen.guru.dto.purchase.OrderCompleteRequest.toEntity;

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
    public List<OrderDetailViewResponse> getPurchaseOrderDetail(String id){
        List<OrderDetailViewResponse> resultList = detailRepository.findByPurchaseOrder(id);
        Set<OrderDetailViewResponse> resultSet = new HashSet<>(resultList);
        List<OrderDetailViewResponse> uniqueResultList = new ArrayList<>(resultSet);

        return uniqueResultList;
    }

    @Override
    public List<PurchaseOrderDetail> getPurchaseOrderPrint(String id){
        return null;
                //detailRepository.findByPurchaseOrder_id(id);
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
        OrderCompleteRequest order = orderRepository.findById(id);
        order.setStatus(newStatus);
        PurchaseOrder orderEntity = toEntity(order);
        orderRepository.save(orderEntity);
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
