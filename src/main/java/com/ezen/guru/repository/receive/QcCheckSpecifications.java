package com.ezen.guru.repository.receive;

import com.ezen.guru.domain.Material;
import com.ezen.guru.domain.QcCheck;
import com.ezen.guru.dto.receive.QcCheckResponse;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class QcCheckSpecifications {
    // where 절 => 진행 상태
    public static Specification<QcCheck> processStatusEquals(int processStatus) {
        return (root, query, criteriaBuilder) -> {
            Join<QcCheck, Material> join = root.join("materialId", JoinType.INNER);

            query.where(
                    criteriaBuilder.equal(root.get("processStatus"), processStatus),
                    criteriaBuilder.equal(join.get("materialId"), root.get("materialId").get("materialId"))
            );

            query.orderBy(criteriaBuilder.desc(root.get("qcCheckId")));
            return query.getRestriction();
        };
    }

    // like 절 => 검색 처리
    public static Specification<QcCheck> searchContains(String search){
        return (root, query, criteriaBuilder) ->{
                Join<QcCheck,Material> join = root.join("materialId",JoinType.INNER);

                query.where(
                        criteriaBuilder.like(root.get("materialId").get("materialName"),
                                "%" + search + "%")
                );
                return query.getRestriction();
        };
    }
}