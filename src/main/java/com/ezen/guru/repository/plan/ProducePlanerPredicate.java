package com.ezen.guru.repository.plan;

import com.ezen.guru.domain.QProducePlaner;
import com.querydsl.core.types.dsl.BooleanExpression;

public class ProducePlanerPredicate {

    private static final QProducePlaner qProducePlaner = QProducePlaner.producePlaner;

    public static BooleanExpression hasCategoryAndProducePlanerId(int category, String keyword) {
        BooleanExpression predicate = qProducePlaner.isNotNull(); // 기본적으로 true로 시작하도록 합니다.

        // 카테고리 조건 추가
        if (category >= 0) {
            predicate = predicate.and(qProducePlaner.producePlanerStatus.eq(category));
        }

        // 키워드 검색 조건 추가 (키워드가 null이 아니고 비어있지 않을 때)
        if (keyword != null && !keyword.trim().isEmpty()) {
            predicate = predicate.and(
                    qProducePlaner.id.producePlanerId.containsIgnoreCase(keyword)
                            .or(qProducePlaner.bicycleName.containsIgnoreCase(keyword))
            );
        }

        return predicate;
    }
}
