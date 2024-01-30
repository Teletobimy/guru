package com.ezen.guru.repository.plan;



import com.querydsl.core.group.GroupBy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ezen.guru.domain.Document;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, String> {
    List<Document> findByType(int type);

    @Query("SELECT d FROM Document d " +
            "WHERE (:type IS NULL OR d.type = :type) " +
            "AND (:id IS NULL OR d.id LIKE %:id%) " +
            "AND (:startDate IS NULL OR d.regdate >= :startDate) " +
            "AND (:endDate IS NULL OR d.regdate <= :endDate)")
    Page<Document> findDocumentsByIdAndDateRange(
            @Param("type") Integer type,
            @Param("id") String id,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );

    @Query("SELECT d FROM Document d " +
            "WHERE (:type IS NULL OR d.type = :type) " +
            "AND (:id IS NULL OR d.id LIKE %:id%) " +
            "AND (:status IS NULL OR d.status = :status) " +
            "AND (:startDate IS NULL OR d.regdate >= :startDate) " +
            "AND (:endDate IS NULL OR d.regdate <= :endDate)")
    Page<Document> procurementList(
            @Param("type") Integer type,
            @Param("id") String id,
            @Param("status") Integer status,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );
}