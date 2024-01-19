package com.ezen.guru.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class JoinDTO {
    private final String userName;
    private final String name;
    private final String password;
    private final String email;
    private final String part;
}
