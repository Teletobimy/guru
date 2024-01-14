package com.ezen.guru.controller.receive;

import com.ezen.guru.domain.Code;
import com.ezen.guru.dto.receive.QcCheckResponse;
import com.ezen.guru.service.receive.QcCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/qcCheck")
@RequiredArgsConstructor
public class QcCheckViewController {
    private final QcCheckService qcCheckService;

    @GetMapping("/qcCheckList")
    public String qcCheckList(Model model,
                              @RequestParam(name = "processStatus", defaultValue = "0") int processStatus,
                              @RequestParam(name = "search", required = false) String search,
                              @PageableDefault(page = 0, size = 10) Pageable pageable,
                              @RequestParam(name = "startDate", required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                              @RequestParam(name = "endDate", required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate){
        Page<QcCheckResponse> page = qcCheckService.qcCheckList(processStatus,search,pageable,startDate,endDate);
        List<Code> codeList = qcCheckService.findByCodeCategory("process_status");

        // 페이지 네이션 계산
        int nowPage = page.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage+9, page.getTotalPages());

        model.addAttribute("list",page);
        model.addAttribute("code",codeList);
        model.addAttribute("processStatus",processStatus);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        return "/shipment/qcCheckList";
    }


}
