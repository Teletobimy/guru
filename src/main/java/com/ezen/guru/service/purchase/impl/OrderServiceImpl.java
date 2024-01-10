package com.ezen.guru.service.purchase.impl;

import com.ezen.guru.domain.Code;
import com.ezen.guru.domain.PurchaseOrder;
import com.ezen.guru.domain.PurchaseOrderDetail;
import com.ezen.guru.dto.purchase.OrderListViewResponse;
import com.ezen.guru.dto.receive.ShipmentResponse;
import com.ezen.guru.repository.CodeRepository;
import com.ezen.guru.repository.purchase.OrderDetailRepository;
import com.ezen.guru.repository.purchase.OrderRepository;
import com.ezen.guru.service.purchase.OrderService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository detailRepository;
    private final CodeRepository codeRepository;

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
}
