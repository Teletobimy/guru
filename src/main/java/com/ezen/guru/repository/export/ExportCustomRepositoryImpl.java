package com.ezen.guru.repository.export;

import com.ezen.guru.domain.Export;
import com.ezen.guru.domain.ProducePlaner;
import com.ezen.guru.domain.QExport;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ExportCustomRepositoryImpl implements ExportCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Export> exportList(int size, int page, String keyword, LocalDateTime startDate, LocalDateTime endDate) {

        QExport qExport = QExport.export;
        BooleanBuilder whereCondition = new BooleanBuilder();

        // 키워드 검색 조건 추가 (키워드가 null이 아니고 비어있지 않을 때)
        if (keyword != null && !keyword.trim().isEmpty()) {
            whereCondition.and(
                    qExport.embeddedId.producePlanerId.like("%" + keyword + "%")
                            .or(qExport.bicycleName.like("%" + keyword + "%"))
            );
        }

        if (startDate != null && endDate != null) {
            whereCondition.and(
                    qExport.exportDate.between(startDate, endDate)
            );
        }

        List<Export> list = jpaQueryFactory
                .selectFrom(qExport)
                .where(
                        Expressions.list(
                                        qExport.embeddedId.producePlanerId,
                                        qExport.embeddedId.materialId
                                ).in(
                                        JPAExpressions
                                                .select(
                                                        qExport.embeddedId.producePlanerId,
                                                        qExport.embeddedId.materialId.min()
                                                )
                                                .from(qExport)
                                                .groupBy(qExport.embeddedId.producePlanerId)
                                )
                                .and(whereCondition)
                )
                .offset(size * page)
                .limit(size)
                .fetch();

        long total = jpaQueryFactory
                .selectFrom(qExport)
                .where(
                        Expressions.list(
                                        qExport.embeddedId.producePlanerId,
                                        qExport.embeddedId.materialId
                                ).in(
                                        JPAExpressions
                                                .select(
                                                        qExport.embeddedId.producePlanerId,
                                                        qExport.embeddedId.materialId.min()
                                                )
                                                .from(qExport)
                                                .groupBy(qExport.embeddedId.producePlanerId)
                                )
                                .and(whereCondition)
                )
                .fetchCount();

        PageRequest pageRequest = PageRequest.of(page, size);
        return new PageImpl<>(list, pageRequest, total);
    }
}
