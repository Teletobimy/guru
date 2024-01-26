package com.ezen.guru.repository.purchase;

import com.ezen.guru.domain.PurchaseOrder;
import com.ezen.guru.domain.QcCheck;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class OrderSpecification {
    public static Specification<PurchaseOrder> statusEquals() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), 3);
    }

    public static Specification<PurchaseOrder> dateRangeFilter(LocalDateTime startDate, LocalDateTime endDate){
        return ((root, query, criteriaBuilder) -> {
            Predicate datePredicate = criteriaBuilder.between(root.get("regdate"),startDate,endDate);
            Predicate finalPredicate = criteriaBuilder.and(datePredicate);

            query.orderBy(criteriaBuilder.desc(root.get("id")));

            return finalPredicate;
        });
    }
}
