package com.ezen.guru.controller.purchase;


import com.ezen.guru.dto.purchase.CompanyListViewResponse;
import com.ezen.guru.dto.purchase.OrderDetailViewResponse;
import com.ezen.guru.dto.purchase.OrderListViewResponse;
import com.ezen.guru.service.purchase.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/company")
@Controller
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/list")
    public String getOrderList(Model model,
                               @RequestParam(value = "size", defaultValue = "10") int size,
                               @RequestParam(value ="page" ,defaultValue = "0") int page,
                               @RequestParam(value = "keyword", required = false) String keyword) {
        Page<CompanyListViewResponse> companyList = companyService.companyList(size, page,keyword);
//        List<CompanyListViewResponse> list = companyService.getCompanyList().stream()
//                .map(CompanyListViewResponse::new)
//                .toList();
        model.addAttribute("list", companyList);
        return "company/list";
    }
}
