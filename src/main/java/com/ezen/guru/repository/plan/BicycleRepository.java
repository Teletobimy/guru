package com.ezen.guru.repository.plan;
import com.ezen.guru.domain.Bicycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BicycleRepository extends JpaRepository<Bicycle, Integer> {
    // 추가적인 쿼리 메서드 등을 선언할 수 있음
}
