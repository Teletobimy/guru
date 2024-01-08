package com.ezen.guru.repository.plan;

import com.ezen.guru.domain.ProducePlaner;
import com.ezen.guru.domain.ProducePlanerId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProducePlanerRepository extends JpaRepository<ProducePlaner, ProducePlanerId> {

    List<ProducePlaner> findByProducePlanerStatusNot(int status);

    List<ProducePlaner> findByIdProducePlanerId(String producePlanerId);
}
