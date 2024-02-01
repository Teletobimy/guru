package com.ezen.guru.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
public class JoinDTO {
    private String userName;
    private String name;
    private String password;
    private String email;
    private String phone;
    private String part;
}
