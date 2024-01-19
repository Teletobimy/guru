package com.ezen.guru.repository.purchase;

import com.ezen.guru.domain.Company;
import com.ezen.guru.dto.purchase.CompanyRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Integer>,CompanyCustomRepository {
    Optional<Object> findByCompanyId(String companyId);

    @Modifying
    @Query("UPDATE company " +
            "SET companyName = :#{#company.companyName}, " +
            "ceo = :#{#company.ceo}, " +
            "tel = :#{#company.tel}, " +
            "email = :#{#company.email}, " +
            "address = :#{#company.address} " +
            "WHERE companyId = :#{#company.companyId}")
    public void update(@RequestBody Company company);

}
