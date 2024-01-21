package com.ezen.guru.service.admin.impl;

import com.ezen.guru.domain.Role;
import com.ezen.guru.domain.User;
import com.ezen.guru.repository.UserRepository;
import com.ezen.guru.repository.UserSpecification;
import com.ezen.guru.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    @Override
    public Page<User> findAll(String part, Pageable pageable) {
        Specification<User> spec = null;
        if(part != null && !part.isEmpty()){
            spec = UserSpecification.partEquals(part);
        }
        Page<User> user = userRepository.findAll(spec,pageable);
        return user;
    }


    @Override
    @Transactional
    public void updateAll(Long userId, String roles) {
        userRepository.updateRoles(userId,roles);
        userRepository.updatePart(userId, roles);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
