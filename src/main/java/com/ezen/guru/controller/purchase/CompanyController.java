package com.ezen.guru.controller.purchase;

import com.ezen.guru.domain.Code;
import com.ezen.guru.dto.purchase.OrderListViewResponse;
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

    @GetMapping("/list")
    public String getOrderList(Model model) {

        return "company/list";
    }
}
