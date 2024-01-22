package com.ezen.guru.dto.purchase;

import com.ezen.guru.domain.Company;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateCompanyRequest {
    private final String companyName;
    private final String ceo;
    private final String tel;
    private final String email;
    private final String address;
}
