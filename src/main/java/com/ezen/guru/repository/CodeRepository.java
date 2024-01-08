package com.ezen.guru.repository;

import com.ezen.guru.domain.Code;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CodeRepository extends JpaRepository<Code,String> {
    Code findByCodeCategoryAndCodeNum(String codeCategory, int codeNum);
    List<Code> findByCodeCategory(String codeCategory);
}