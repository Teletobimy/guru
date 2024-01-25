package com.ezen.guru.repository.export;

import com.ezen.guru.domain.Export;
import com.ezen.guru.domain.ProducePlanerId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExportRepository extends JpaRepository<Export, ProducePlanerId> {

    List<Export> findByEmbeddedIdProducePlanerId(String producePlanerId);
    @Transactional
    @Modifying
    @Query("DELETE FROM Export e where e.embeddedId.producePlanerId = :producePlanerId")
    void deleteByEmbeddedIdProducePlanerId(@Param("producePlanerId") String producePlanerId);
}
