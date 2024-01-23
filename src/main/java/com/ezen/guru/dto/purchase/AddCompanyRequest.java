package com.ezen.guru.dto.purchase;

import com.ezen.guru.domain.Company;
import com.ezen.guru.dto.purchase.validation.Unique;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddCompanyRequest {

    @Unique
    @NotBlank(message = "사업자번호를 입력해주세요.")
    String companyId;

    @NotBlank(message = "회사명을 입력해주세요.")
    String companyName;

    @NotBlank(message = "대표자명을 입력해주세요.")
    String companyCeo;

    @NotBlank(message = "전화번호를 입력해주세요.")
    String companyTel;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    String companyEmail;

    @NotBlank(message = "주소를 입력해주세요.")
    String companyAddress;

    public Company toEntity() {
        return Company.builder()
                .companyId(companyId)
                .companyName(companyName)
                .ceo(companyCeo)
                .tel(companyTel)
                .email(companyEmail)
                .address(companyAddress)
                .build();
    }
}
