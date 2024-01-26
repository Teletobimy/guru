package com.ezen.guru.controller.export;

import com.ezen.guru.domain.Code;
import com.ezen.guru.dto.UserDTO;
import com.ezen.guru.dto.export.ExportDTO;
import com.ezen.guru.dto.export.IdRequest;
import com.ezen.guru.dto.plan.BicycleDTO;
import com.ezen.guru.dto.plan.MaterialDTO;
import com.ezen.guru.dto.plan.ProducePlanerDTO;
import com.ezen.guru.dto.purchase.CompanyListViewResponse;
import com.ezen.guru.service.CustomUserDetails;
import com.ezen.guru.service.export.ExportService;
import com.ezen.guru.service.plan.BicycleService;
import com.ezen.guru.service.plan.MaterialService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/export")
@RequiredArgsConstructor
public class ExportController {

    private final ExportService exportService;
    private final MaterialService materialService;
    private final BicycleService bicycleService;

    // 공통 유저 정보 설정 메소드
    private UserDTO getUser(HttpServletRequest request) {
        CustomUserDetails userDetails = (CustomUserDetails) request.getSession().getAttribute("user");
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        return new UserDTO(userDetails.getUserId(),
                userDetails.getUsername(),
                userDetails.getName(),
                userDetails.getEmail(),
                userDetails.getPart(),
                roles,
                userDetails.getPhone());
    }

    @GetMapping("/producePlanerList")
    public String producePlanerList(Model model, HttpServletRequest request,
                                    @RequestParam(value = "size", defaultValue = "10") int size,
                                    @RequestParam(value ="page" ,defaultValue = "0") int page,
                                    @RequestParam(value = "keyword", required = false) String keyword,
                                    @RequestParam(value = "category", defaultValue = "-1", required = false) int category,
                                    @RequestParam(name = "startDate", required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                    @RequestParam(name = "endDate", required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        //공통 유저 정보
        UserDTO user = getUser(request);
        model.addAttribute("user",user);


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

    @GetMapping("/producePlanerRegister")
    public String producePlanerRegister(Model model, HttpServletRequest request){

        //공통 유저 정보
        UserDTO user = getUser(request);
        model.addAttribute("user",user);

        //생산계획서 번호 생성(생성시점 날짜&시간)
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        String formattedDate = now.format(formatter);
        System.out.println("producePlanerId : " + formattedDate);

        model.addAttribute("producePlanerId", formattedDate);

        return "export/producePlanerRegister";
    }

    @GetMapping("/bicycleSearch")
    public String company_search(Model model, HttpServletRequest request,
                                 @RequestParam(value = "size", defaultValue = "10") int size,
                                 @RequestParam(value ="page" ,defaultValue = "0") int page,
                                 @RequestParam(value = "keyword", required = false) String keyword) {

        //공통 유저 정보
        UserDTO user = getUser(request);
        model.addAttribute("user",user);

        Page<BicycleDTO> bicycles = bicycleService.getAllBicycles(keyword, PageRequest.of(page, size));

        model.addAttribute("bicycles", bicycles);
        return "export/bicycleSearch";
    }

    @GetMapping("/producePlanerDetail")
    public String producePlanerDetail(@RequestParam("producePlanerId") String producePlanerId, Model model, HttpServletRequest request) {

        //공통 유저 정보
        UserDTO user = getUser(request);
        model.addAttribute("user",user);

        List<ProducePlanerDTO> list = exportService.findByProducePlanerId(producePlanerId);
        List<Code> codeList = exportService.findByCodeList(list);
        List<MaterialDTO> mList = new ArrayList<>();

        for (ProducePlanerDTO dto : list) {
            mList.add(materialService.getMaterialById(dto.getMaterialId()));
        }

        model.addAttribute("producePlanerList", list);
        model.addAttribute("materialList", mList);
        model.addAttribute("codeList", codeList);

        return "export/producePlanerDetail";
    }

    @GetMapping("/exportList")
    public String exportList(Model model, HttpServletRequest request,
                                    @RequestParam(value = "size", defaultValue = "10") int size,
                                    @RequestParam(value ="page" ,defaultValue = "0") int page,
                                    @RequestParam(value = "keyword", required = false) String keyword,
                                    @RequestParam(name = "startDate", required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                    @RequestParam(name = "endDate", required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        //공통 유저 정보
        UserDTO user = getUser(request);
        model.addAttribute("user",user);

        //출고데이터
        Page<ExportDTO> exportList = exportService.findExportList(size, page, keyword, startDate, endDate);
        List<ProducePlanerDTO> producePlanerList = new ArrayList<>();

        for (ExportDTO dto : exportList) {
            producePlanerList.add(exportService.findById(dto.getProducePlanerId(), dto.getBicycleId(), dto.getMaterialId()));
        }
        List<Code> code = exportService.findByCodeCategory("produce_planer_status");

        model.addAttribute("exportList", exportList);
        model.addAttribute("producePlanerList", producePlanerList);
        model.addAttribute("code", code);

        return "export/exportList";
    }

    @GetMapping("/exportDetail")
    public String exportDetail(@RequestParam("producePlanerId") String producePlanerId, Model model, HttpServletRequest request) {

        //공통 유저 정보
        UserDTO user = getUser(request);
        model.addAttribute("user",user);

        List<ExportDTO> export = exportService.findExport(producePlanerId);
        List<ProducePlanerDTO> list = exportService.findByProducePlanerId(producePlanerId);
        System.out.println("exportList : " + export);
        System.out.println("producePlanerList : " + list);

        model.addAttribute("exportList", export);
        model.addAttribute("producePlaner", list);

        return "export/exportDetail";
    }

    @GetMapping("/materialStock")
    public String materialList(Model model, HttpServletRequest request,
                               @RequestParam(value="size", defaultValue = "10") int size,
                               @RequestParam(value="page", defaultValue = "0") int page,
                               @RequestParam(value = "keyword", required = false, defaultValue = "") String materialName,
                               @RequestParam(value = "category", required = false, defaultValue = "-1") Integer  category) {

        //공통 유저 정보
        UserDTO user = getUser(request);
        model.addAttribute("user",user);

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
    public String productList(Model model, HttpServletRequest request,
                              @RequestParam(value="size", defaultValue = "10") int size,
                              @RequestParam(value="page", defaultValue = "0") int page,
                              @RequestParam(value = "keyword", defaultValue = "", required = false) String bicycleName) {

        //공통 유저 정보
        UserDTO user = getUser(request);
        model.addAttribute("user",user);

        Page<BicycleDTO> bicycles = bicycleService.getAllBicycles(bicycleName, PageRequest.of(page, size));
        model.addAttribute("bicycles", bicycles);

        return "export/productStock";
    }

    @PostMapping("/producePlanerDetail")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_D')")
    public ResponseEntity<?> producePlanerDetail(@RequestBody ProducePlanerDTO request) {

        System.out.println("----------------detail controller");
        try {
            System.out.println("----------------try");
            exportService.updateStatus(request);
            System.out.println("----------------success update");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/deleteExport")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_D')")
    public ResponseEntity<?> cancelExport(@RequestBody IdRequest request) {

        System.out.println("deleteExport---------------------------");
        try {
            exportService.deleteExport(request.getProducePlanerId());
            System.out.println("deleteOk---------------------------");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/listExport")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_D')")
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
