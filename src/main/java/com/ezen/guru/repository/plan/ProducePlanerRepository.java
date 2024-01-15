package com.ezen.guru.repository.plan;

import com.ezen.guru.domain.ProducePlaner;
import com.ezen.guru.domain.ProducePlanerId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProducePlanerRepository extends JpaRepository<ProducePlaner, ProducePlanerId>, QuerydslPredicateExecutor<ProducePlaner> {

    List<ProducePlaner> findByIdProducePlanerId(String producePlanerId);
    @Modifying
    @Query("UPDATE ProducePlaner p SET p.producePlanerStatus = 99 WHERE p.id.producePlanerId = :producePlanerId")
    void updateProducePlanerStatusById(@Param("producePlanerId") String producePlanerId);
}
