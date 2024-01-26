package com.ezen.guru.repository.purchase;

import com.ezen.guru.dto.purchase.OrderListViewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.time.LocalDateTime;

public interface OrderCustomRepository {
    Page<OrderListViewResponse> orderList(int size, int page, String keyword, int category, LocalDateTime startDate, LocalDateTime endDate);
}
