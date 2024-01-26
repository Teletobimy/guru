package com.ezen.guru.repository.purchase.impl;

import com.ezen.guru.domain.QCompany;
import com.ezen.guru.domain.QPurchaseOrder;
import com.ezen.guru.domain.QPurchaseOrderDetail;
import com.ezen.guru.dto.purchase.CompanyListViewResponse;
import com.ezen.guru.dto.purchase.OrderListViewResponse;
import com.ezen.guru.repository.purchase.CompanyCustomRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class CompanyCustomRepositoryImpl implements CompanyCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<CompanyListViewResponse> companyList(int size, int page, String keyword) {

        QCompany qCompany = QCompany.company;

        BooleanBuilder whereCondition = new BooleanBuilder();

        if(keyword != null && !keyword.isEmpty()){
            whereCondition.and(qCompany.companyName.like("%" + keyword + "%"));
        }

        QueryResults<CompanyListViewResponse> results = jpaQueryFactory
                .selectDistinct(Projections.constructor(
                        CompanyListViewResponse.class,
                        qCompany.companyId,
                        qCompany.companyName,
                        qCompany.ceo,
                        qCompany.tel,
                        qCompany.email,
                        qCompany.zipcode,
                        qCompany.address
                ))
                .from(qCompany)
                .where(whereCondition)
                .orderBy(qCompany.companyId.asc())
                .offset(size * page)
                .limit(size)
                .fetchResults();
        // 결과를 Page 객체로 변환
        List<CompanyListViewResponse> content = results.getResults();
        long total = results.getTotal();
        PageRequest pageRequest = PageRequest.of(page,size);
        return new PageImpl<>(content,pageRequest,total);
    }
}
