package com.ezen.guru.repository.plan;

import com.ezen.guru.domain.ProducePlaner;
import com.ezen.guru.domain.QProducePlaner;
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
public class ProducePlanerCustomRepositoryImpl implements ProducePlanerCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<ProducePlaner> producePlanerList(int size, int page, int category, String keyword, LocalDateTime startDate, LocalDateTime endDate) {

        QProducePlaner qProducePlaner = QProducePlaner.producePlaner;
        BooleanBuilder whereCondition = new BooleanBuilder();

        // 카테고리 조건 추가
        if (category == 1) {
            whereCondition.and(
                    qProducePlaner.embeddedId.producePlanerId.in(
                            jpaQueryFactory
                                    .select(qProducePlaner.embeddedId.producePlanerId)
                                    .from(qProducePlaner)
                                    .groupBy(qProducePlaner.embeddedId.producePlanerId)
                                    .having(qProducePlaner.producePlanerStatus.avg().notIn(0.00, 2.00, 99.00))
                    )
            );
        } else if (category == 0) {
            whereCondition.and(
                    qProducePlaner.embeddedId.producePlanerId.in(
                            jpaQueryFactory
                                    .select(qProducePlaner.embeddedId.producePlanerId)
                                    .from(qProducePlaner)
                                    .groupBy(qProducePlaner.embeddedId.producePlanerId)
                                    .having(qProducePlaner.producePlanerStatus.avg().eq(0.00))
                    )
            );
        } else if (category == 2) {
            whereCondition.and(
                    qProducePlaner.embeddedId.producePlanerId.in(
                            jpaQueryFactory
                                    .select(qProducePlaner.embeddedId.producePlanerId)
                                    .from(qProducePlaner)
                                    .groupBy(qProducePlaner.embeddedId.producePlanerId)
                                    .having(qProducePlaner.producePlanerStatus.avg().eq(2.00))
                    )
            );
        } else if (category == 99) {
            whereCondition.and(
                    qProducePlaner.embeddedId.producePlanerId.in(
                            jpaQueryFactory
                                    .select(qProducePlaner.embeddedId.producePlanerId)
                                    .from(qProducePlaner)
                                    .groupBy(qProducePlaner.embeddedId.producePlanerId)
                                    .having(qProducePlaner.producePlanerStatus.avg().eq(99.00))
                    )
            );
        }

        // 키워드 검색 조건 추가 (키워드가 null이 아니고 비어있지 않을 때)
        if (keyword != null && !keyword.trim().isEmpty()) {
            whereCondition.and(
                    qProducePlaner.embeddedId.producePlanerId.like("%" + keyword + "%")
                            .or(qProducePlaner.bicycleName.like("%" + keyword + "%"))
            );
        }

        if (startDate != null && endDate != null) {
            whereCondition.and(
                    qProducePlaner.producePlanerDeadline.between(startDate, endDate)
            );
        }

        List<ProducePlaner> list = jpaQueryFactory
                .selectFrom(qProducePlaner)
                .where(
                        Expressions.list(
                                qProducePlaner.embeddedId.producePlanerId,
                                qProducePlaner.embeddedId.materialId
                        ).in(
                                JPAExpressions
                                        .select(
                                                qProducePlaner.embeddedId.producePlanerId,
                                                qProducePlaner.embeddedId.materialId.min()
                                        )
                                        .from(qProducePlaner)
                                        .groupBy(qProducePlaner.embeddedId.producePlanerId)
                        )
                                .and(whereCondition)
                )
                .offset(size * page)
                .limit(size)
                .fetch();

        long total = jpaQueryFactory
                .selectFrom(qProducePlaner)
                .where(
                        Expressions.list(
                                        qProducePlaner.embeddedId.producePlanerId,
                                        qProducePlaner.embeddedId.materialId
                                ).in(
                                        JPAExpressions
                                                .select(
                                                        qProducePlaner.embeddedId.producePlanerId,
                                                        qProducePlaner.embeddedId.materialId.min()
                                                )
                                                .from(qProducePlaner)
                                                .groupBy(qProducePlaner.embeddedId.producePlanerId)
                                )
                                .and(whereCondition)
                )
                .fetchCount();

        PageRequest pageRequest = PageRequest.of(page, size);
        return new PageImpl<>(list, pageRequest, total);
    }
}
