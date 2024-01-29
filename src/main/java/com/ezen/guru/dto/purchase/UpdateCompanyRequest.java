package com.ezen.guru.dto.purchase;

import com.ezen.guru.domain.Company;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateCompanyRequest {

    @NotBlank(message = "회사명을 입력해주세요.")
    private final String companyName;

    @NotBlank(message = "대표자명을 입력해주세요.")
    private final String ceo;

    @NotBlank(message = "전화번호를 입력해주세요.")
    private final String tel;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private final String email;

    @NotBlank(message = "주소를 입력해주세요.")
    private final String zipcode;

    @NotBlank(message = "주소를 입력해주세요.")
    private final String address;

    public UpdateCompanyRequest() {
        this.companyName = null;
        this.ceo = null;
        this.tel = null;
        this.email = null;
        this.zipcode = null;
        this.address = null;
    }
}
