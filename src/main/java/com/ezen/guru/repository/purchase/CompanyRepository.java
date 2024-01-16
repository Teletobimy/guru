package com.ezen.guru.repository.purchase;

import com.ezen.guru.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    Optional<Object> findByCompanyId(String companyId);
}
