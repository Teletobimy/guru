package com.ezen.guru.controller.receive;

import com.ezen.guru.domain.Code;
import com.ezen.guru.domain.QcCheck;
import com.ezen.guru.dto.UserDTO;
import com.ezen.guru.dto.receive.ShipmentDetailResponse;
import com.ezen.guru.dto.receive.ShipmentResponse;
import com.ezen.guru.service.CustomUserDetails;
import com.ezen.guru.service.receive.ShipmentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/shipment")
@RequiredArgsConstructor
public class ShipmentViewController {

    private final ShipmentService shipmentService;

    @GetMapping("/shipmentList")
    public String shipmentList(Model model,
                               HttpServletRequest request,
                               @RequestParam(name = "size", defaultValue = "10") int size,
                               @RequestParam(name ="page" ,defaultValue = "0") int page,
                               @RequestParam(name = "keyword", required = false) String keyword,
                               @RequestParam(name = "category", defaultValue = "-1") int category,
                               @RequestParam(name = "startDate", required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime startDate,
                               @RequestParam(name = "endDate", required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime endDate)
    {
        Page<ShipmentResponse> shipmentList = shipmentService.shipmentList(size, page,keyword,category,startDate,endDate);
        List<Code> codeList = shipmentService.findByCodeCategory("material_category");
        List<QcCheck> qcCheckList = shipmentService.qcCheckList();
        int nowPage = shipmentList.getPageable().getPageNumber() + 1;
        boolean prevPage = nowPage > 1;
        boolean nextPage = nowPage < shipmentList.getTotalPages();

        model.addAttribute("list",shipmentList);
        model.addAttribute("code",codeList);
        model.addAttribute("category",category);
        model.addAttribute("qcCheckList",qcCheckList);
        model.addAttribute("prev", prevPage);
        model.addAttribute("next", nextPage);
        model.addAttribute("nowPage" , nowPage);

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
        return "/shipment/shipmentList";

    }

    @GetMapping("/shipmentDetail")
    public String shipmentDetail(Model model,
                                 HttpServletRequest request,
                                 @RequestParam(value = "shipmentId") int shipmentId) {
        ShipmentDetailResponse shipmentDetail = shipmentService.findByShimentId(shipmentId);
        QcCheck qcCheck = shipmentService.qcCheck(shipmentId);
        model.addAttribute("shipment", shipmentDetail);
        model.addAttribute("qcCheck", qcCheck);

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

        return "/shipment/shipmentDetail";
    }

    @PostMapping("/addQcCheck")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_C')")
    public ResponseEntity<String> addQcCheck(@RequestParam(value = "shipmentId") int shipmentId){
        try {
            shipmentService.addQcCheck(shipmentId);
            return ResponseEntity.status(HttpStatus.FOUND).header("Location","/shipment/shipmentList").build();
        } catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
