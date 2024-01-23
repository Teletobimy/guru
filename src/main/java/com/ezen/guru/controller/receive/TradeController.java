package com.ezen.guru.controller.receive;

import com.ezen.guru.dto.UserDTO;
import com.ezen.guru.dto.receive.TradeDTO;
import com.ezen.guru.service.CustomUserDetails;
import com.ezen.guru.service.receive.TradeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class TradeController {
    private final TradeService tradeService;

    @GetMapping("/tradeList")
    public String tradeList(Model model,
                            HttpServletRequest request,
                            @PageableDefault(page = 0, size = 10)Pageable pageable,
                            @RequestParam(name = "startDate", required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                            @RequestParam(name = "endDate", required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate){
        Page<TradeDTO> page = tradeService.tradeList(pageable,startDate,endDate);

        int nowPage = page.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 9, page.getTotalPages());
        boolean prevPage = nowPage > 1;
        boolean nextPage = nowPage < page.getTotalPages();

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
        model.addAttribute("list", page);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("prevPage", prevPage);
        model.addAttribute("nextPage",nextPage);
        model.addAttribute("user",user);
        return "/shipment/tradeList";
    }
}
