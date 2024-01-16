package com.ezen.guru.repository.plan;


import com.ezen.guru.domain.Quotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuotationRepository extends JpaRepository<Quotation, String> {
    // 추가적인 쿼리 메소드가 필요하다면 여기에 작성할 수 있습니다.
}