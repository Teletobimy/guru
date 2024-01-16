package com.ezen.guru.repository.plan;

import com.ezen.guru.domain.Bicycle;
import com.ezen.guru.dto.plan.BicycleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BicycleRepository extends CrudRepository<Bicycle, Integer> {

    @Query("SELECT b FROM Bicycle b WHERE " +
            "(:bicycleName IS NULL OR LOWER(b.bicycleName) LIKE LOWER(CONCAT('%', :bicycleName, '%'))) " +
            "ORDER BY b.bicycleId ASC")
    Page<Bicycle> findAllByBicycleNameContainingIgnoreCaseOrderByBicyclePriceAsc(@Param("bicycleName") String bicycleName, Pageable pageable);

    Page<Bicycle> findAll(Pageable pageable);
}
