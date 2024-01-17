package com.ezen.guru.dto.purchase;

import com.ezen.guru.domain.Company;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
public class CompanyListViewResponse {

    private final String companyId;
    private final String companyName;
    private final String ceo;
    private final String tel;
    private final String email;
    private final String address;

//    public CompanyListViewResponse(Company company) {
//        this.companyId = company.getCompanyId();
//        this.companyName = company.getCompanyName();
//        this.ceo = company.getCeo();
//        this.tel = company.getTel();
//        this.email = company.getEmail();
//        this.address = company.getAddress();
//    }
}
