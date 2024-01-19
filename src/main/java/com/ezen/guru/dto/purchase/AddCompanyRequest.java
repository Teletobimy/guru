package com.ezen.guru.dto.purchase;

import com.ezen.guru.domain.Company;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddCompanyRequest {
    String companyId;
    String companyName;
    String companyCeo;
    String companyTel;
    String companyEmail;
    String companyAddress;

    public Company toEntity() {
        return Company.builder()
                .companyId(companyId)
                .companyName(companyName)
                .ceo(companyCeo)
                .tel(companyTel)
                .address(companyAddress)
                .build();
    }
}
