package com.ezen.guru.dto.purchase;

import com.ezen.guru.domain.Company;

public class CompanyListViewResponse {

    public String companyId;
    public String companyName;
    public String ceo;
    public String tel;
    public String email;
    public String address;

    public CompanyListViewResponse(Company company) {
        this.companyId = company.getCompanyId();
        this.companyName = company.getCompanyName();
        this.ceo = company.getCeo();
        this.tel = company.getTel();
        this.email = company.getEmail();
        this.address = company.getAddress();
    }
}
