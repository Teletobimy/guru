package com.ezen.guru.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Data
public class UserDTO {
    private final Long userId;
    private final String userName;
    private final String name;
    private final String email;
    private final String part;
    private final String roles;
    private final String phone;

    public UserDTO(Long userId,String userName, String name, String email, String part, Set<String> roles, String phone) {
        this.userId = userId;
        this.userName = userName;
        this.name = name;
        this.email = email;
        this.part = part;
        this.roles = String.join(", ", roles); // Set<String>을 쉼표로 구분된 문자열로 변환
        this.phone = phone;
    }
}
