package com.ezen.guru.repository.plan;

import com.ezen.guru.domain.ProducePlaner;
import com.ezen.guru.domain.ProducePlanerId;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;

public interface ProducePlanerRepository extends JpaRepository<ProducePlaner, ProducePlanerId>, QuerydslPredicateExecutor<ProducePlaner> {

    Page<ProducePlaner> findAllByPage(BooleanExpression predicate, Pageable pageable);
    List<ProducePlaner> findByIdProducePlanerId(String producePlanerId);
    @Modifying
    @Query("UPDATE ProducePlaner p SET p.producePlanerStatus = 99 WHERE p.id.producePlanerId = :producePlanerId")
    void updateProducePlanerStatusById(@Param("producePlanerId") String producePlanerId);
}
