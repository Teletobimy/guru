package com.ezen.guru.controller.receive;

import com.ezen.guru.domain.Code;
import com.ezen.guru.domain.QcCheck;
import com.ezen.guru.dto.receive.ShipmentDetailResponse;
import com.ezen.guru.dto.receive.ShipmentResponse;
import com.ezen.guru.service.receive.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/shipment")
@RequiredArgsConstructor
public class ShipmentViewController {

    private final ShipmentService shipmentService;

    @GetMapping("/shipmentList")
    public String shipmentList(Model model,
                               @RequestParam(value = "size", defaultValue = "10") int size,
                               @RequestParam(value ="page" ,defaultValue = "0") int page,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "category", defaultValue = "-1") int category)
    {
        Page<ShipmentResponse> shipmentList = shipmentService.shipmentList(size, page,keyword,category);
        List<Code> codeList = shipmentService.findByCodeCategory("material_category");
        List<QcCheck> qcCheckList = shipmentService.qcCheckList();
        model.addAttribute("list",shipmentList);
        model.addAttribute("code",codeList);
        model.addAttribute("category",category);
        model.addAttribute("qcCheckList",qcCheckList);

        return "/shipment/shipmentList";

    }

    @GetMapping("/shipmentDetail")
    public String shipmentDetail(Model model,
                                 @RequestParam(value = "shipmentId") int shipmentId) {
        ShipmentDetailResponse shipmentDetail = shipmentService.findByShimentId(shipmentId);
        QcCheck qcCheck = shipmentService.qcCheck(shipmentId);
        model.addAttribute("shipment", shipmentDetail);
        model.addAttribute("qcCheck", qcCheck);

        return "/shipment/shipmentDetail";
    }

    @PostMapping("/addQcCheck")
    public ResponseEntity<String> addQcCheck(@RequestParam int shipmentId){
        try {
            shipmentService.addQcCheck(shipmentId);
            return ResponseEntity.status(HttpStatus.FOUND).header("Location","/shipment/shipmentList").build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
