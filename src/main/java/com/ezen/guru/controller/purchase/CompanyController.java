package com.ezen.guru.controller.purchase;

import com.ezen.guru.domain.Code;
import com.ezen.guru.dto.purchase.CompanyListViewResponse;
import com.ezen.guru.dto.purchase.OrderListViewResponse;
import com.ezen.guru.dto.purchase.OrderPrintViewResponse;
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
    public String getOrderList(Model model) {
        List<CompanyListViewResponse> list = companyService.getCompanyList().stream()
                .map(CompanyListViewResponse::new)
                .toList();

        model.addAttribute("list", list);

        return "company/list";
    }
}
