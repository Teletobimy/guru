package com.ezen.guru.repository.plan;


import com.ezen.guru.domain.Quotation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuotationRepository extends CrudRepository<Quotation, String> {
    Page<Quotation> findAll(Pageable pageable);
    // 추가적인 쿼리 메소드가 필요하다면 여기에 작성할 수 있습니다.
}