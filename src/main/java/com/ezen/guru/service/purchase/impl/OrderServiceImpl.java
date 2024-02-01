package com.ezen.guru.service.purchase.impl;

import com.ezen.guru.domain.*;
import com.ezen.guru.dto.purchase.*;
import com.ezen.guru.repository.CodeRepository;
import com.ezen.guru.repository.purchase.CompanyRepository;
import com.ezen.guru.repository.purchase.OrderDetailRepository;
import com.ezen.guru.repository.purchase.OrderRepository;
import com.ezen.guru.repository.receive.ShipmentRepository;
import com.ezen.guru.service.purchase.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository detailRepository;
    private final CodeRepository codeRepository;
    private final ShipmentRepository shipmentRepository;

    @Override
    public Page<OrderListViewResponse> orderList(int size, int page, String keyword, int category, LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.orderList(size, page, keyword, category,startDate,endDate);
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
    public List<OrderPrintViewResponse> getPurchaseOrderPrint(String id){
        List<OrderPrintViewResponse> resultList = detailRepository.getPrintPage(id);
        Set<OrderPrintViewResponse> resultSet = new HashSet<>(resultList);
        List<OrderPrintViewResponse> uniqueResultList = new ArrayList<>(resultSet);

        return uniqueResultList;
    }
    @Override
    public void updateOrderDetailStatus(int orderId, int newStatus) {
        PurchaseOrderDetail order = detailRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("주문을 찾을 수 없습니다."));
        order.setCheck(newStatus);
        detailRepository.save(order);
    }

    @Override
    @Transactional
    public void updateOrderStatus(String id) {
        // OrderCompleteRequest에서 PurchaseOrder로 변환
        orderRepository.changeStatus(id);
//        purchaseOrder.update(request.getNewStatus());
        // PurchaseOrder 업데이트
//        orderRepository.save(purchaseOrder);
    }

    @Override
    @Transactional
    public void closeOrder() {
        orderRepository.closeOrder();
    }

    @Override
    @Transactional
    public void forceClose(String id) {
        orderRepository.forceClose(id);
    }

    @Override
    @Transactional
    public void updateOrderCnt(int id, int orderCnt) {
        try {
            detailRepository.updateOrderCnt(id, orderCnt);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Shipment> saveToShipment(List<AddShipmentRequest> shipments) {
        List<Shipment> shipmentEntities = shipments.stream()
                .map(AddShipmentRequest::toEntity) // toEntity 메서드 사용
                .collect(Collectors.toList());
        return shipmentRepository.saveAll(shipmentEntities);
    }

    @Override
    public List<OrderMainListResponse> getOrderMainList() {
        return orderRepository.orderMainList();
    }

    @Override
    public Long countBy() {
        return orderRepository.countBy();
    }

    @Override
    public Long countByStatus(int status) {
        return orderRepository.countByStatus(status);
    }

}
