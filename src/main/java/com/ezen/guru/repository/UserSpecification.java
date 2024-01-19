package com.ezen.guru.repository;

import com.ezen.guru.domain.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {
    public static Specification<User> partEquals(String part){
        return ((root, query, criteriaBuilder) -> {
            query.where(
                    criteriaBuilder.equal(root.get("part"),part)
            );
            query.orderBy(criteriaBuilder.asc(root.get("userId")));
            return query.getRestriction();
        });
    }
}
