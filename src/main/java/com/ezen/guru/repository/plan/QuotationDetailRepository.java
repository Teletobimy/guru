package com.ezen.guru.repository.plan;


import com.ezen.guru.domain.Quotation;
import com.ezen.guru.domain.QuotationDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuotationDetailRepository extends CrudRepository<QuotationDetail, String> {
    List<QuotationDetail> findAllByQuotation(Quotation quotation);
}