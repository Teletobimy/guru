package com.ezen.guru.controller.receive;

import com.ezen.guru.domain.QcCheck;
import com.ezen.guru.dto.receive.QcCheckRequest;
import com.ezen.guru.service.receive.QcCheckService;
import com.ezen.guru.service.receive.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class QcCheckApiController {
    private final QcCheckService qcCheckService;
    private final ShipmentService shipmentService;
    @PutMapping("/updateQcCheckCnt")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_C')")
    public ResponseEntity<String> updatepurchase(@RequestParam int qcCheckId, @RequestParam int qcCheckCnt){
        try {
            qcCheckService.updateAllStatus(qcCheckId,qcCheckCnt);
            return ResponseEntity.ok("Update successful for qcCheckId: " + qcCheckId + "qcCheckCnt: " + qcCheckCnt);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update fail" + e.getMessage());
        }
    }
    @GetMapping("/qcCheckModal")
    public ResponseEntity<QcCheck> qcCheckModal(@RequestParam(value = "shipmentId") int shipmentId){
        QcCheck qcCheck = shipmentService.qcCheck(shipmentId);
        return new ResponseEntity<>(qcCheck,HttpStatus.OK);
    }

    @PostMapping("/fromQcCheckToShipment")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_C')")
    public ResponseEntity<String> addShipment(@RequestParam(value = "cnt") int cnt, @RequestBody QcCheckRequest shipment){
        qcCheckService.addShipment(shipment,cnt);
        return ResponseEntity.ok("Seccessful insert shipment! : " + shipment);
    }
    @PutMapping("/updateReturnStatus")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_C')")
    public ResponseEntity<String> updateReturnStatus(@RequestParam(value = "qcCheckId") int qcCheckId,@RequestParam(value = "qcCheckCnt") int qcCheckCnt){
        try {
            qcCheckService.updateReturnStatus(qcCheckId,qcCheckCnt);
            return ResponseEntity.ok("Update successful for qcCheckId: " + qcCheckId + "qcCheckCnt: " + qcCheckCnt);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Update fail" + e.getMessage());
        }
    }
}
