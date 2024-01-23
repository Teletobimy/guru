package com.ezen.guru.service.receive.impl;

import com.ezen.guru.domain.PurchaseOrder;
import com.ezen.guru.dto.receive.TradeDTO;
import com.ezen.guru.repository.purchase.OrderRepository;
import com.ezen.guru.repository.purchase.OrderSpecification;
import com.ezen.guru.service.receive.TradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TradeServiceImpl implements TradeService {
    private final OrderRepository orderRepository;

    @Override
    public Page<TradeDTO> tradeList(Pageable pageable, LocalDateTime startDate, LocalDateTime endDate) {
        Specification<PurchaseOrder> spec = Specification.where(null);
        spec = spec.and(OrderSpecification.statusEquals());
        if(startDate != null && endDate != null){
            spec = spec.and(OrderSpecification.dateRangeFilter(startDate, endDate));
        }
        Page<PurchaseOrder> tradePage =orderRepository.findAll(spec,pageable);

        return tradePage.map(purchaseOrder -> new TradeDTO(
                purchaseOrder.getId(),
                purchaseOrder.getCompany().getCompanyName(),
                purchaseOrder.getTotalprice(),
                purchaseOrder.getRegdate()
        ));
    }
}
