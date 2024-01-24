package com.ezen.guru.controller.receive;

import com.ezen.guru.domain.Code;
import com.ezen.guru.dto.UserDTO;
import com.ezen.guru.dto.receive.QcCheckResponse;
import com.ezen.guru.service.CustomUserDetails;
import com.ezen.guru.service.receive.QcCheckService;
import com.ezen.guru.service.receive.ShipmentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/qcCheck")
@RequiredArgsConstructor
public class QcCheckViewController {
    private final QcCheckService qcCheckService;
    private final ShipmentService shipmentService;

    @GetMapping("/qcCheckList")
    public String qcCheckList(HttpServletRequest request,
                              Model model,
                              @RequestParam(name = "processStatus", defaultValue = "0") int processStatus,
                              @RequestParam(name = "search", required = false) String search,
                              @PageableDefault(page = 0, size = 10, sort = "qcCheckId", direction = Sort.Direction.DESC) Pageable pageable,
                              @RequestParam(name = "startDate", required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                              @RequestParam(name = "endDate", required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate){
        Page<QcCheckResponse> page = qcCheckService.qcCheckList(processStatus,search,pageable,startDate,endDate);
        List<Code> codeList = qcCheckService.findByCodeCategory("process_status");

        // 페이지 네이션 계산
        int nowPage = page.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage+9, page.getTotalPages());
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

        model.addAttribute("user",user);
        model.addAttribute("list",page);
        model.addAttribute("code",codeList);
        model.addAttribute("processStatus",processStatus);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("prevPage", prevPage);
        model.addAttribute("nextPage",nextPage);
        return "/shipment/qcCheckList";
    }


}
