package com.ezen.guru.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserInfoDTO {
    private final String name;
    private final String email;
    private final String phone;
}
