package com.ezen.guru.service.purchase;

import com.ezen.guru.domain.PurchaseOrderDetail;
import com.ezen.guru.repository.purchaseOrder.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public List<PurchaseOrderDetail> getProductOrderDetailsByPurchaseOrderStatus() {
        return orderRepository.findByPurchaseOrder_PurchaseOrderStatus(0);
    }
}
