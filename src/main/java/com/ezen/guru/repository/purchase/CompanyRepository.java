package com.ezen.guru.repository.purchase;

import com.ezen.guru.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer>,CompanyCustomRepostory {
}
