package com.ezen.guru.repository.purchase.impl;

import com.ezen.guru.domain.QCompany;
import com.ezen.guru.domain.QPurchaseOrder;
import com.ezen.guru.domain.QPurchaseOrderDetail;
import com.ezen.guru.dto.purchase.OrderListViewResponse;
import com.ezen.guru.repository.purchase.OrderCustomRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Arrays.stream;

@RequiredArgsConstructor
@Repository
public class OrderCustomRepositoryImpl implements OrderCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<OrderListViewResponse> orderList(int size, int page, String keyword, int category, LocalDateTime startDate, LocalDateTime endDate) {

        QPurchaseOrder qOrder = QPurchaseOrder.purchaseOrder;
        QPurchaseOrderDetail qOrderDetail = QPurchaseOrderDetail.purchaseOrderDetail;
        QCompany qCompany = QCompany.company;

        BooleanBuilder whereCondition = new BooleanBuilder();

        if(keyword != null && !keyword.isEmpty()){
            whereCondition.and(qOrder.id.like("%" + keyword + "%"));
        }
        if(category != -1){
            whereCondition.and(qOrder.status.eq(category));
        }
        if(startDate != null && endDate != null){
            whereCondition.and(qOrder.deadline.between(startDate, endDate));
        }

        QueryResults<OrderListViewResponse> results = jpaQueryFactory
                .selectDistinct(Projections.constructor(
                        OrderListViewResponse.class,
                        qOrderDetail.purchaseOrder.id,
                        qOrder.status,
                        qCompany.companyName,
                        Expressions.stringTemplate("MIN({0})", qOrderDetail.materialName),
                        qOrder.totalprice,
                        qOrder.deadline
                ))
                .from(qOrder)
                .leftJoin(qOrderDetail)
                .on(qOrder.id.eq(qOrderDetail.purchaseOrder.id))
                .join(qCompany)
                .on(qOrder.company.companyId.eq(qCompany.companyId))
                .where(whereCondition)
                .groupBy(qOrder.id, qOrder.status, qCompany.companyName, qOrder.totalprice, qOrder.deadline)
                .orderBy(qOrder.deadline.asc())
                .offset(size * page)
                .limit(size)
                .fetchResults();
        // 결과를 Page 객체로 변환
         List<OrderListViewResponse> content = results.getResults();
         long total = results.getTotal();
         PageRequest pageRequest = PageRequest.of(page,size);
        return new PageImpl<>(content,pageRequest,total);
    }
}
