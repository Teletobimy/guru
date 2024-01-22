package com.ezen.guru.service.admin;

import com.ezen.guru.domain.User;
import com.ezen.guru.dto.UserInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface AdminService {
    public Page<User> findAll(String part, Pageable pageable);
    public void deleteUser(Long userId);
    public void updateAll(Long userId, String roles);

    public void updateUserInfo(UserInfoDTO infoDTO, Long userId);
}
