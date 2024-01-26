package com.ezen.guru.dto.purchase.validation;

import com.ezen.guru.repository.purchase.CompanyRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueValidator implements ConstraintValidator<Unique, String> {
    // 중복 검사 로직
    @Autowired
    private CompanyRepository companyRepository;
    @Override
    public boolean isValid(String companyId, ConstraintValidatorContext context) {
        return companyId != null && !companyRepository.existsByCompanyId(companyId);
    }
}
