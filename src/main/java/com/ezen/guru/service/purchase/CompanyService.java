package com.ezen.guru.service.purchase;

import com.ezen.guru.domain.Company;
import com.ezen.guru.dto.purchase.AddCompanyRequest;
import com.ezen.guru.dto.purchase.CompanyListViewResponse;
import com.ezen.guru.dto.purchase.UpdateCompanyRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CompanyService {

    public List<Company> getCompanyList();
    public Page<CompanyListViewResponse> companyList(int size, int page, String keyword);
    public Company newCompany(AddCompanyRequest company);
    public void updateCompany(String companyId, UpdateCompanyRequest company);

}
