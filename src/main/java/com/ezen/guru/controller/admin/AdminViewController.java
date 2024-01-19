package com.ezen.guru.controller.admin;

import com.ezen.guru.domain.Code;
import com.ezen.guru.domain.User;
import com.ezen.guru.dto.UserDTO;
import com.ezen.guru.service.CustomUserDetails;
import com.ezen.guru.service.admin.AdminService;
import com.ezen.guru.service.receive.QcCheckService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminViewController {

    private final AdminService adminService;
    private final QcCheckService qcCheckService;
    @GetMapping("/userList")
    public String userList(Model model,
                           HttpServletRequest request,
                           @RequestParam(name = "part", defaultValue = "전체보기")String part,
                           @PageableDefault(page = 0, size = 10)Pageable pageable){

        // 전체 사용자 리스트
        Page<User> userPage;
        if (part.equals("전체보기")) {
            userPage = adminService.findAll(null, pageable);
        } else {
            userPage = adminService.findAll(part, pageable);
        }

        List<Code> codeList = qcCheckService.findByCodeCategory("part_category");

        int nowPage = userPage.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage+9, userPage.getTotalPages());

        CustomUserDetails userDetails = (CustomUserDetails) request.getSession().getAttribute("user");
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        UserDTO user = new UserDTO(userDetails.getUserId(), userDetails.getUsername(),userDetails.getName(), userDetails.getEmail(), userDetails.getPart(),roles);

        model.addAttribute("user",user);
        model.addAttribute("userPage",userPage);
        model.addAttribute("code",codeList);
        model.addAttribute("part",part);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        return "/admin/userList";
    }
}
