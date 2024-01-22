package com.ezen.guru.repository.purchase;

import com.ezen.guru.domain.Company;
import com.ezen.guru.dto.purchase.UpdateCompanyRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CompanyRepository extends JpaRepository<Company, Integer>,CompanyCustomRepository {
    Company findByCompanyId(String companyId);

    @Modifying
    @Query("UPDATE Company " +
            "SET companyName = :#{#company.companyName}, " +
            "ceo = :#{#company.ceo}, " +
            "tel = :#{#company.tel}, " +
            "email = :#{#company.email}, " +
            "address = :#{#company.address} " +
            "WHERE companyId = :companyId")
    public void update(@Param("companyId") String companyId, @Param("company") UpdateCompanyRequest company);
}
