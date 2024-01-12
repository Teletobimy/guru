package com.ezen.guru.service.receive;

import com.ezen.guru.domain.Code;
import com.ezen.guru.dto.receive.QcCheckResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QcCheckService {
    public Page<QcCheckResponse> qcCheckList(int processStatus, String search, Pageable pageable);
    public List<Code> findByCodeCategory(String codeCategory);
}
