package com.ezen.guru.repository.plan;

import com.ezen.guru.domain.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {
    // 추가적인 Material 관련 메서드가 필요한 경우 여기에 추가할 수 있습니다.
}
