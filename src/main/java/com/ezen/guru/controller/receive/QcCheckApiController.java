package com.ezen.guru.controller.receive;

import com.ezen.guru.domain.QcCheck;
import com.ezen.guru.service.receive.QcCheckService;
import com.ezen.guru.service.receive.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class QcCheckApiController {
    private final QcCheckService qcCheckService;
    private final ShipmentService shipmentService;
    @PutMapping("/updateQcCheckCnt")
    public ResponseEntity<String> updatepurchase(@RequestParam int qcCheckId, @RequestParam int qcCheckCnt){
        try {
            qcCheckService.updateAllStatus(qcCheckId,qcCheckCnt);
            return ResponseEntity.ok("Update successful for qcCheckId: " + qcCheckId + "qcCheckCnt: " + qcCheckCnt);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update fail" + e.getMessage());
        }
    }
    @GetMapping("/qcCheckModal")
    public ResponseEntity<QcCheck> qcCheckModal(@RequestParam int shipmentId){
        QcCheck qcCheck = shipmentService.qcCheck(shipmentId);
        return new ResponseEntity<>(qcCheck,HttpStatus.OK);
    }
}
