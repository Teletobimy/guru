package com.ezen.guru.repository.export;

import com.ezen.guru.domain.Export;
import com.ezen.guru.domain.ProducePlaner;
import com.ezen.guru.domain.ProducePlanerId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExportRepository extends JpaRepository<Export, ProducePlanerId> {

    List<Export> findByIdProducePlanerId(String producePlanerId);
}
