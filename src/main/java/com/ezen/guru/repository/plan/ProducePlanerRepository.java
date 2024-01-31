package com.ezen.guru.repository.plan;

import com.ezen.guru.domain.ProducePlaner;
import com.ezen.guru.domain.ProducePlanerId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProducePlanerRepository extends JpaRepository<ProducePlaner, ProducePlanerId>, QuerydslPredicateExecutor<ProducePlaner> {

    ProducePlaner findByEmbeddedId(ProducePlanerId id);
    List<ProducePlaner> findByEmbeddedIdProducePlanerId(String producePlanerId);
    @Transactional
    @Modifying
    @Query("UPDATE ProducePlaner p SET p.producePlanerStatus = :status WHERE p.embeddedId.producePlanerId = :producePlanerId")
    void updateProducePlanerStatusById(@Param("producePlanerId") String producePlanerId, @Param("status") int status);
    @Transactional
    @Modifying
    @Query("DELETE FROM ProducePlaner p WHERE p.embeddedId.producePlanerId = :producePlanerId")
    void deleteProducePlanerById(@Param("producePlanerId") String producePlanerId);
}
