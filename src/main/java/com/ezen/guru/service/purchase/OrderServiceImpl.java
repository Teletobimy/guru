package com.ezen.guru.service.purchase;

import com.ezen.guru.domain.Code;
import com.ezen.guru.domain.PurchaseOrder;
import com.ezen.guru.domain.PurchaseOrderDetail;
import com.ezen.guru.dto.purchase.OrderListViewResponse;
import com.ezen.guru.dto.receive.ShipmentResponse;
import com.ezen.guru.repository.CodeRepository;
import com.ezen.guru.repository.purchase.OrderDetailRepository;
import com.ezen.guru.repository.purchase.OrderRepository;
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
}
