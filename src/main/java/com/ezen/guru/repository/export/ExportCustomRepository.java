package com.ezen.guru.repository.export;

import com.ezen.guru.domain.Export;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public interface ExportCustomRepository {

    Page<Export> exportList(int size, int page, String keyword, LocalDateTime startDate, LocalDateTime endDate);
}
