package com.ezen.guru.controller.export;

import com.ezen.guru.domain.Code;
import com.ezen.guru.dto.export.ExportDTO;
import com.ezen.guru.dto.export.IdRequest;
import com.ezen.guru.dto.plan.BicycleDTO;
import com.ezen.guru.dto.plan.MaterialDTO;
import com.ezen.guru.dto.plan.ProducePlanerDTO;
import com.ezen.guru.service.export.ExportService;
import com.ezen.guru.service.plan.BicycleService;
import com.ezen.guru.service.plan.MaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/export")
@RequiredArgsConstructor
public class ExportController {

    private final ExportService exportService;
    private final MaterialService materialService;
    private final BicycleService bicycleService;

    @GetMapping("/producePlanerList")
    public String producePlanerList(Model model,
                                    @RequestParam(value = "size", defaultValue = "10") int size,
                                    @RequestParam(value ="page" ,defaultValue = "0") int page,
                                    @RequestParam(value = "keyword", required = false) String keyword,
                                    @RequestParam(value = "category", defaultValue = "-1", required = false) int category,
                                    @RequestParam(name = "startDate", required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                    @RequestParam(name = "endDate", required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        System.out.println("category : " + category);
        Page<ProducePlanerDTO> producePlanerDTOList = exportService.findAll(size, page, category, keyword, startDate, endDate);
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

    @GetMapping("/materialStock")
    public String materialList(Model model,
                               @RequestParam(value="size", defaultValue = "10") int size,
                               @RequestParam(value="page", defaultValue = "0") int page,
                               @RequestParam(value = "keyword", required = false, defaultValue = "") String materialName,
                               @RequestParam(value = "category", required = false, defaultValue = "-1") Integer  category) {
        System.out.println("--------------------");
        System.out.println("category : " + category);
        System.out.println("keyword : " + materialName);
        Page<MaterialDTO> materials = materialService.getAllMaterials(materialName, category, PageRequest.of(page, size));
        List<Code> code = exportService.findByCodeCategory("material_category");

        model.addAttribute("materials", materials);
        model.addAttribute("category", category);
        model.addAttribute("code", code);

        return "export/materialStock";
    }

    @GetMapping("/productStock")
    public String productList(Model model,
                              @RequestParam(value="size", defaultValue = "10") int size,
                              @RequestParam(value="page", defaultValue = "0") int page,
                              @RequestParam(value = "keyword", defaultValue = "", required = false) String bicycleName) {

        Page<BicycleDTO> bicycles = bicycleService.getAllBicycles(bicycleName, PageRequest.of(page, size));
        model.addAttribute("bicycles", bicycles);

        return "export/productStock";
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
