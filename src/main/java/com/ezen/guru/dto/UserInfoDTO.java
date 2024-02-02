package com.ezen.guru.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
public class UserInfoDTO {
    private String name;
    private String email;
    private String phone;
}
