package com.ezen.guru.controller;

import com.ezen.guru.dto.UserDTO;
import com.ezen.guru.dto.UserInfoDTO;
import com.ezen.guru.service.CustomUserDetails;
import com.ezen.guru.service.admin.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/my")
@RequiredArgsConstructor
public class UserController {
    private final AdminService adminService;

    @GetMapping("/userInfo")
    public String userInfo(Model model, HttpServletRequest request){
        CustomUserDetails userDetails = (CustomUserDetails) request.getSession().getAttribute("user");
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        UserDTO user = new UserDTO(userDetails.getUserId(),
                userDetails.getUsername(),
                userDetails.getName(),
                userDetails.getEmail(),
                userDetails.getPart(),
                roles,
                userDetails.getPhone());

        model.addAttribute("user",user);
        return "/my/userInfo";
    }

    @PutMapping("/updateUserInfo")
    public ResponseEntity<String> updateUserInfo(@RequestBody UserInfoDTO infoDTO,
                                                 @RequestParam Long userId){
        try {
            adminService.updateUserInfo(infoDTO,userId);
            return ResponseEntity.ok("Success Update User~!");
        }catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
