package com.ezen.guru.controller.export;

import com.ezen.guru.domain.Code;
import com.ezen.guru.dto.export.ExportDTO;
import com.ezen.guru.dto.export.IdRequest;
import com.ezen.guru.dto.plan.ProducePlanerDTO;
import com.ezen.guru.service.export.ExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/export")
@RequiredArgsConstructor
public class ExportController {

    private final ExportService exportService;

    @GetMapping("/producePlanerList")
    public String producePlanerList(Model model,
                                    @RequestParam(value = "size", defaultValue = "10") int size,
                                    @RequestParam(value ="page" ,defaultValue = "0") int page,
                                    @RequestParam(value = "keyword", required = false) String keyword,
                                    @RequestParam(value = "category", defaultValue = "-1", required = false) Integer category) {

        System.out.println("category : " + category);
        Page<ProducePlanerDTO> producePlanerDTOList = exportService.findAll(page, size, category, keyword);
        List<Code> codeList = exportService.setCodeListByProducePlanerStatus(producePlanerDTOList);
        List<Code> code = exportService.findByCodeCategory("produce_planer_status");

        for (ProducePlanerDTO producePlanerDTO : producePlanerDTOList) {
            System.out.println("controller list id : " + producePlanerDTO.getProducePlanerId());
        }
        model.addAttribute("producePlanerList", producePlanerDTOList);
        model.addAttribute("category", category);
        model.addAttribute("codeList", codeList);
        model.addAttribute("code", code);

        return "export/producePlanerList";
    }

    @GetMapping("/producePlanerDetail")
    public String producePlanerDetail(@RequestParam("producePlanerId") String producePlanerId, Model model) {

        List<ProducePlanerDTO> list = exportService.findByProducePlanerId(producePlanerId);
        List<Code> codeList = exportService.findByCodeList(list);

        model.addAttribute("producePlanerList", list);
        model.addAttribute("codeList", codeList);

        return "export/producePlanerDetail";
    }

    @PostMapping("/producePlanerDetail")
    public ResponseEntity<?> producePlanerDetail(@RequestBody ExportDTO request) {

        try {
            exportService.addExport(request);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/cancelExport")
    public ResponseEntity<?> cancelExport(@RequestBody ExportDTO request) {

        try {
            exportService.deleteExport(request);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/listExport")
    public ResponseEntity<String> handleListExport(@RequestBody IdRequest request) {
        try {
            // producePlanerId 값으로 수행할 작업 수행
            System.out.println("Received producePlanerId: " + request.getProducePlanerId());

            // 여기에 추가적인 비즈니스 로직을 수행
            exportService.listExport(request.getProducePlanerId());

            return new ResponseEntity<>("Data successfully received and processed", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to process data", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
