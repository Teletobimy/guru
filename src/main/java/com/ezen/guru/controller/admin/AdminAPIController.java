package com.ezen.guru.controller.admin;

import com.ezen.guru.domain.Role;
import com.ezen.guru.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminAPIController {
    private final AdminService adminService;
    @PutMapping("/updateRoles")
    public ResponseEntity<String> updateRoles(@RequestParam Long userId, @RequestParam String roles){
        try {
            adminService.updateAll(userId,roles);
            return ResponseEntity.ok("Update Success" + userId);
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update Fail" +  e.getMessage());
        }
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestParam Long userId){
        try {
            adminService.deleteUser(userId);
            return ResponseEntity.ok("Delete Success" + userId);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Delete Fail" + e.getMessage());
        }
    }
}
