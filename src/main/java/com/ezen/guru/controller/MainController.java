package com.ezen.guru.controller;

import com.ezen.guru.dto.UserDTO;
import com.ezen.guru.service.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class MainController {
    @GetMapping("/")
    public String main(HttpServletRequest request, Model model){
        CustomUserDetails userDetails = (CustomUserDetails) request.getSession().getAttribute("user");

        if(userDetails == null){
            return "index";
        }

        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        UserDTO user = new UserDTO(userDetails.getUserId(),userDetails.getUsername(),userDetails.getName(), userDetails.getEmail(), userDetails.getPart(),roles);

        model.addAttribute("user",user);

        return "index";
    }
}
