package com.ezen.guru.service.purchase;

import com.ezen.guru.domain.PurchaseOrder;
import com.ezen.guru.domain.PurchaseOrderDetail;
import com.ezen.guru.repository.purchase.OrderDetailRepository;
import com.ezen.guru.repository.purchase.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository detailRepository;

    public List<PurchaseOrder> getProductBeforeOrderDetailsByStatus(int category) {
        return orderRepository.findByStatusOrderByDeadline(0);
    }

    public List<PurchaseOrder> getProductAfterOrderDetailsByStatus(){
        return orderRepository.findByStatusOrderByDeadline(1);
    }

    public List<PurchaseOrder> getProductDeliveringOrderDetailsByStatus(){
        return orderRepository.findByStatusOrderByDeadline(2);
    }

    public List<PurchaseOrder> getProductClosedOrderDetailsByStatus(){
        return orderRepository.findByStatusOrderByDeadline(3);
    }

    public List<PurchaseOrderDetail> getPurchaseOrderDocument(String id){
        return detailRepository.findByPurchaseOrder_id(id);
    }
}
