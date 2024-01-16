package com.ezen.guru.controller.receive;

import com.ezen.guru.domain.QcCheck;
import com.ezen.guru.dto.receive.QcCheckRequest;
import com.ezen.guru.dto.receive.ShipmentDetailResponse;
import com.ezen.guru.service.receive.QcCheckService;
import com.ezen.guru.service.receive.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/fromQcCheckToShipment")
    public ResponseEntity<String> addShipment(@RequestParam int cnt, @RequestBody QcCheckRequest shipment){
        qcCheckService.addShipment(shipment,cnt);
        return ResponseEntity.ok("Seccessful insert shipment! : " + shipment);
    }
    @PutMapping("/updateReturnStatus")
    public ResponseEntity<String> updateReturnStatus(@RequestParam int qcCheckId,@RequestParam int qcCheckCnt){
        try {
            qcCheckService.updateReturnStatus(qcCheckId,qcCheckCnt);
            return ResponseEntity.ok("Update successful for qcCheckId: " + qcCheckId + "qcCheckCnt: " + qcCheckCnt);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update fail" + e.getMessage());
        }
    }
}