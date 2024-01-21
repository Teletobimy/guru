package com.ezen.guru.repository;

import com.ezen.guru.domain.Role;
import com.ezen.guru.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor<User> {
    boolean existsByUserName(String userName);

    User findByUserName(String username);

    Page<User> findAll(Specification<User> spec, Pageable pageable);

    @Modifying
    @Transactional
    @Query(nativeQuery = true,value = "UPDATE user_roles SET roles = :roles WHERE user_Id = :userId")
    void updateRoles(@Param("userId") Long userId, @Param("roles")String roles);

    @Modifying
    @Query("UPDATE User SET part = :part WHERE userId = :userId")
    void updatePart(@Param("userId") Long userId,@Param("part") String part);

}
