package com.ezen.guru.repository.purchase.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CompanyCustomRepositoryImpl {

    private final JPAQueryFactory jpaQueryFactory;


}
