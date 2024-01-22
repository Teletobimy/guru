package com.ezen.guru.service.purchase.impl;

import com.ezen.guru.domain.Company;
import com.ezen.guru.dto.purchase.AddCompanyRequest;
import com.ezen.guru.dto.purchase.CompanyListViewResponse;
import com.ezen.guru.dto.purchase.UpdateCompanyRequest;
import com.ezen.guru.repository.purchase.CompanyRepository;
import com.ezen.guru.service.purchase.CompanyService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public List<Company> getCompanyList() {
        return companyRepository.findAll();
    }

    @Override
    public Page<CompanyListViewResponse> companyList(int size, int page, String keyword) {
        return companyRepository.companyList(size, page, keyword);
    }
    @Override
    public Company newCompany(AddCompanyRequest company) {
        return companyRepository.save(company.toEntity());
    }

    @Transactional
    @Override
    public void updateCompany(String companyId, UpdateCompanyRequest company) {
        companyRepository.update(companyId, company);
    }

}
