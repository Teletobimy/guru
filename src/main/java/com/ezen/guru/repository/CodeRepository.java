package com.ezen.guru.repository;

import com.ezen.guru.domain.Code;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CodeRepository extends JpaRepository<Code,String> {
    Code findByCodeNumAndCodeCategory(int codeNum, String codeCategory);
    List<Code> findByCodeCategory(String codeCategory);
}
