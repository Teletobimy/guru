package com.ezen.guru.service.admin;

import com.ezen.guru.domain.Role;
import com.ezen.guru.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface AdminService {
    public Page<User> findAll(String part, Pageable pageable);
    public void deleteUser(Long userId);
    public void updateAll(Long userId, String roles);
}
