package com.ezen.guru.controller.purchase;


import com.ezen.guru.domain.Company;
import com.ezen.guru.dto.purchase.AddCompanyRequest;
import com.ezen.guru.dto.purchase.CompanyListViewResponse;
import com.ezen.guru.dto.purchase.OrderDetailViewResponse;
import com.ezen.guru.dto.purchase.OrderListViewResponse;
import com.ezen.guru.service.purchase.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/purchase")
@Controller
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/company_list")
    public String getOrderList(Model model,
                               @RequestParam(value = "size", defaultValue = "10") int size,
                               @RequestParam(value ="page" ,defaultValue = "0") int page,
                               @RequestParam(value = "keyword", required = false) String keyword) {
        Page<CompanyListViewResponse> companyList = companyService.companyList(size, page,keyword);
//        List<CompanyListViewResponse> list = companyService.getCompanyList().stream()
//                .map(CompanyListViewResponse::new)
//                .toList();
        model.addAttribute("list", companyList);
        return "purchase/company_list";
    }

    @PostMapping("/company/add")
    public ResponseEntity<String> addToCompany(@RequestBody AddCompanyRequest company) {
        Company newCompany = companyService.newCompany(company);
        return new ResponseEntity<>("협력사가 등록되었습니다.", HttpStatus.OK);
    }
}
