package com.ezen.guru.controller;

import com.ezen.guru.domain.PurchaseOrder;
import com.ezen.guru.dto.UserDTO;
import com.ezen.guru.dto.purchase.OrderMainListResponse;
import com.ezen.guru.service.CustomUserDetails;
import com.ezen.guru.service.purchase.OrderService;
import com.ezen.guru.service.receive.QcCheckService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final QcCheckService qcCheckService;
    private final OrderService orderService;

    @GetMapping("/")
    public String main(HttpServletRequest request, Model model){
        CustomUserDetails userDetails = (CustomUserDetails) request.getSession().getAttribute("user");

        if(userDetails == null){
            return "index";
        }

        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        UserDTO user = new UserDTO(userDetails.getUserId(),userDetails.getUsername(),userDetails.getName(), userDetails.getEmail(), userDetails.getPart(),roles,userDetails.getPhone());

        long totalCount = qcCheckService.countBy();
        long statusCount = qcCheckService.countByProcessStatus(3);

        double percent = ((double) statusCount / totalCount) * 100;
        int totalPercent = (int)percent;

        List<OrderMainListResponse> orderList = orderService.getOrderMainList();

        long orderTotalCount = orderService.countBy();
        long orderStatusCount = orderService.countByStatus(3);
        double orderPercent = ((double) orderStatusCount / orderTotalCount) * 100;
        int orderTotalPercent = (int)orderPercent;

        model.addAttribute("user",user);
        model.addAttribute("percent",totalPercent);
        model.addAttribute("total",totalCount);
        model.addAttribute("status",statusCount);
        model.addAttribute("polist", orderList);
        model.addAttribute("pototal",orderTotalCount);
        model.addAttribute("postatus",orderStatusCount);
        model.addAttribute("orderPercent",orderTotalPercent);
        return "index";
    }
}
