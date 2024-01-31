package com.ezen.guru.dto;

import com.ezen.guru.service.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class UserDTO {
    private Long userId;
    private String userName;
    private String name;
    private String email;
    private String part;
    private String roles;
    private String phone;

    public UserDTO(Long userId,String userName, String name, String email, String part, Set<String> roles, String phone) {
        this.userId = userId;
        this.userName = userName;
        this.name = name;
        this.email = email;
        this.part = part;
        this.roles = String.join(", ", roles); // Set<String>을 쉼표로 구분된 문자열로 변환
        this.phone = phone;
    }

    // 공통 유저 정보 설정 메소드
    public UserDTO getUser(HttpServletRequest request) {
        CustomUserDetails userDetails = (CustomUserDetails) request.getSession().getAttribute("user");
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        return new UserDTO(userDetails.getUserId(),
                userDetails.getUsername(),
                userDetails.getName(),
                userDetails.getEmail(),
                userDetails.getPart(),
                roles,
                userDetails.getPhone());
    }
}
