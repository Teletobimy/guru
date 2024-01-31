package com.ezen.guru.controller.export;

import com.ezen.guru.domain.Bicycle;
import com.ezen.guru.domain.Code;
import com.ezen.guru.domain.Recipe;
import com.ezen.guru.dto.UserDTO;
import com.ezen.guru.dto.export.ExportDTO;
import com.ezen.guru.dto.export.IdRequest;
import com.ezen.guru.dto.plan.*;
import com.ezen.guru.dto.purchase.CompanyListViewResponse;
import com.ezen.guru.service.CustomUserDetails;
import com.ezen.guru.service.export.ExportService;
import com.ezen.guru.service.plan.BicycleService;
import com.ezen.guru.service.plan.MaterialService;
import com.ezen.guru.service.plan.RecipeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.stream.Stream;

@Controller
@RequestMapping("/export")
@RequiredArgsConstructor
public class ExportController {

    private final ExportService exportService;
    private final MaterialService materialService;
    private final BicycleService bicycleService;
    private final RecipeService recipeService;

    private final UserDTO userDTO = new UserDTO();

    @GetMapping("/producePlanerList")
    public String producePlanerList(Model model, HttpServletRequest request,
                                    @RequestParam(value = "size", defaultValue = "10") int size,
                                    @RequestParam(value ="page" ,defaultValue = "0") int page,
                                    @RequestParam(value = "keyword", required = false) String keyword,
                                    @RequestParam(value = "category", defaultValue = "-1", required = false) int category,
                                    @RequestParam(name = "startDate", required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                    @RequestParam(name = "endDate", required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        //공통 유저 정보
        UserDTO user = userDTO.getUser(request);
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
        UserDTO user = userDTO.getUser(request);
        model.addAttribute("user",user);

        //생산계획서 번호 생성(생성시점 날짜&시간)
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        String formattedDate = now.format(formatter);
        System.out.println("producePlanerId : " + formattedDate);

        model.addAttribute("producePlanerId", formattedDate);

        return "export/producePlanerRegister";
    }

    @GetMapping("/producePlanerUpdate")
    public String producePlanerUpdate(Model model, HttpServletRequest request, @RequestParam("producePlanerId") String producePlanerId){

        //공통 유저 정보
        UserDTO user = userDTO.getUser(request);
        model.addAttribute("user",user);

        List<ProducePlanerDTO> producePlanerList = exportService.findByProducePlanerId(producePlanerId);
        model.addAttribute("producePlanerList",producePlanerList);
        model.addAttribute("producePlanerId",producePlanerId);

        return "export/producePlanerUpdate";
    }

    @GetMapping("/bicycleSearch")
    public String bicycleSearch(Model model, HttpServletRequest request,
                                 @RequestParam(value = "size", defaultValue = "10") int size,
                                 @RequestParam(value ="page" ,defaultValue = "0") int page,
                                 @RequestParam(value = "keyword", required = false) String keyword) {

        //공통 유저 정보
        UserDTO user = userDTO.getUser(request);
        model.addAttribute("user",user);

        Page<BicycleDTO> bicycles = bicycleService.getAllBicycles(keyword, PageRequest.of(page, size));

        model.addAttribute("bicycles", bicycles);
        return "export/bicycleSearch";
    }

    @GetMapping("/materialSearch")
    public String materialSearch(Model model, HttpServletRequest request,
                                 @RequestParam(value = "size", defaultValue = "10") int size,
                                 @RequestParam(value ="page" ,defaultValue = "0") int page,
                                 @RequestParam(value = "keyword", required = false) String keyword,
                                 @RequestParam(value = "rowIndex", required = false) int rowIndex) {

        //공통 유저 정보
        UserDTO user = userDTO.getUser(request);
        model.addAttribute("user",user);

        System.out.println("materialName : " + keyword);
        System.out.println("rowIndex : " + rowIndex);
        Page<MaterialDTO> materials = materialService.getAllMaterials(keyword, -1, PageRequest.of(page, size));

        model.addAttribute("materials", materials);
        model.addAttribute("rowIndex", rowIndex);
        return "export/materialSearch";
    }

    @GetMapping("/getRecipeList")
    public ResponseEntity<?> getRecipeList(Model model, HttpServletRequest request, @RequestParam(name = "bicycleId") int bicycleId) {

        System.out.println("-----------bicycleId : " + bicycleId);

        //공통 유저 정보
        UserDTO user = userDTO.getUser(request);
        model.addAttribute("user",user);

        Bicycle bicycle = bicycleService.getBicycleById(bicycleId);
        List<Recipe> recipe = recipeService.findByBicycle(bicycle);
        List<RecipeDTO> recipeList = recipe.stream().map(RecipeDTO::new).toList();

        System.out.println("----recipe : " + recipe);
        System.out.println("---------recipeList : " + recipeList);

        try {
//            String jsonRecipeList = objectMapper.writeValueAsString(recipeList);
            model.addAttribute("recipeList", recipeList);
            return ResponseEntity.ok(recipeList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while processing JSON");
        }
    }

    @GetMapping("/choiceRecipe")
    public String choiceRecipe(Model model, HttpServletRequest request,
                                 @RequestParam(value = "size", defaultValue = "10") int size,
                                 @RequestParam(value ="page" ,defaultValue = "0") int page,
                                 @RequestParam(value = "keyword", required = false) String keyword) {

        //공통 유저 정보
        UserDTO user = userDTO.getUser(request);
        model.addAttribute("user",user);

        Page<BicycleDTO> bicycles = bicycleService.getAllBicycles(keyword, PageRequest.of(page, size));

        model.addAttribute("bicycles", bicycles);
        return "export/recipeSearch";
    }

    @GetMapping("/producePlanerDetail")
    public String producePlanerDetail(@RequestParam("producePlanerId") String producePlanerId, Model model, HttpServletRequest request) {

        //공통 유저 정보
        UserDTO user = userDTO.getUser(request);
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
        UserDTO user = userDTO.getUser(request);
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
        UserDTO user = userDTO.getUser(request);
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
                               @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                               @RequestParam(value = "category", required = false, defaultValue = "-1") Integer category) {

        //공통 유저 정보
        UserDTO user = userDTO.getUser(request);
        model.addAttribute("user",user);

        System.out.println("--------------------");
        System.out.println("category : " + category);
        System.out.println("keyword : " + keyword);
        Page<GroupingMaterialDTO> materials = materialService.getAllByMaterialName(keyword, category, PageRequest.of(page, size));
        List<Code> code = exportService.findByCodeCategory("material_category");

        model.addAttribute("materials", materials);
        model.addAttribute("category", category);
        model.addAttribute("code", code);

        return "export/materialStock";
    }

    @GetMapping("/materialDetailStock")
    public String materialDetailStock(Model model, HttpServletRequest request,
                               @RequestParam(value="size", defaultValue = "10") int size,
                               @RequestParam(value="page", defaultValue = "0") int page,
                               @RequestParam(value = "category", required = false, defaultValue = "") int category,
                               @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                               @RequestParam(value = "materialName", required = false, defaultValue = "") String materialName) {

        //공통 유저 정보
        UserDTO user = userDTO.getUser(request);
        model.addAttribute("user",user);

        System.out.println("--------------------");
        System.out.println("keyword : " + keyword);
        System.out.println("materialName : " + materialName);
        Page<MaterialDTO> materials = materialService.findByCompanyName(keyword, materialName, category, PageRequest.of(page, size));
        List<Code> code = exportService.findByCodeCategory("material_category");

        model.addAttribute("materialName", materialName);
        model.addAttribute("materials", materials);
        model.addAttribute("category", category);
        model.addAttribute("code", code);

        return "export/materialDetailStock";
    }

    @GetMapping("/productStock")
    public String productList(Model model, HttpServletRequest request,
                              @RequestParam(value="size", defaultValue = "10") int size,
                              @RequestParam(value="page", defaultValue = "0") int page,
                              @RequestParam(value = "keyword", defaultValue = "", required = false) String bicycleName) {

        //공통 유저 정보
        UserDTO user = userDTO.getUser(request);
        model.addAttribute("user",user);

        Page<BicycleDTO> bicycles = bicycleService.getAllBicycles(bicycleName, PageRequest.of(page, size));
        model.addAttribute("bicycles", bicycles);

        return "export/productStock";
    }

    @PostMapping("/producePlanerRegister")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_D')")
    public ResponseEntity<?> producePlanerRegister(@RequestBody List<ProducePlanerDTO> request) {

        System.out.println("----------------producePlanerRegister controller");
        try {
            System.out.println("----------------try");
            exportService.saveProducePlaner(request);
            System.out.println("----------------success save");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/producePlanerDelete")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_D')")
    public ResponseEntity<?> producePlanerDelete(@RequestBody IdRequest request) {

        System.out.println("----------------producePlanerRegister controller");
        try {
            System.out.println("----------------try");
            exportService.deleteProducePlaner(request.getProducePlanerId());
            System.out.println("----------------success save");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
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
