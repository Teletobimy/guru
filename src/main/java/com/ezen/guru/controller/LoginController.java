package com.ezen.guru.controller;

import com.ezen.guru.service.CustomUserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(){

        return "login";
    }
}
