package com.ezen.guru.repository.purchase;

import com.ezen.guru.dto.purchase.CompanyListViewResponse;
import org.springframework.data.domain.Page;

public interface CompanyCustomRepository {
    Page<CompanyListViewResponse> companyList(int size, int page, String keyword);
}
