package com.ezen.guru.repository.plan;


import com.ezen.guru.domain.Quotation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface QuotationRepository extends CrudRepository<Quotation, String> {
    Page<Quotation> findAll(Pageable pageable);

    @Query("SELECT q FROM Quotation q " +
            "WHERE (:biddingNo IS NULL OR q.biddingNo = :biddingNo) " +
            "AND (:status IS NULL OR q.status = :status) " +
            "AND (:startDate IS NULL OR q.regdate >= :startDate) " +
            "AND (:endDate IS NULL OR q.regdate <= :endDate)")
    Page<Quotation> quotationList(
            @Param("biddingNo") Integer biddingNo,
            @Param("status") Integer status,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );

    @Query("SELECT q FROM Quotation q " +
            "WHERE (:biddingNo IS NULL OR q.biddingNo = :biddingNo) " +
            "AND (:startDate IS NULL OR q.regdate >= :startDate) " +
            "AND (:endDate IS NULL OR q.regdate <= :endDate) ")
    Page<Quotation> findQuotationsByBiddingNoAndDateRange(
            @Param("biddingNo") Integer biddingNo,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );


    List<Quotation> findAllByBiddingNoOrderByStatusDesc(int biddingNo);
}