package com.ezen.guru.controller.plan;



import com.ezen.guru.config.RecipeId;
import com.ezen.guru.domain.*;
import com.ezen.guru.dto.UserDTO;
import com.ezen.guru.dto.plan.*;
import com.ezen.guru.dto.purchase.CompanyListViewResponse;
import com.ezen.guru.service.CustomUserDetails;
import com.ezen.guru.service.plan.*;
import com.ezen.guru.service.purchase.CompanyService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.SplittableRandom;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Controller
@RequestMapping("/plan")
public class PlanController {

    private final BicycleService bicycleService;
    private final MaterialService materialService;
    private final DocumentService documentService;
    private final QuotationService quotationService;
    private final CompanyService companyService;
    private final RecipeService recipeService;


    @GetMapping("/item")
    public String item(Model model,
                       @RequestParam(value="size", defaultValue = "10") int size,
                       @RequestParam(value="page", defaultValue = "0") int page,
                       @RequestParam(value = "bicycleName", required = false) String bicycleName,
                       HttpServletRequest request){
        Page<BicycleDTO> bicyclePage = bicycleService.getAllBicycles(bicycleName, PageRequest.of(page, size, Sort.by(Sort.Order.desc("bicycleId"))));
        List<BicycleDTO> bicycles = bicyclePage.getContent();
        if(bicycleName==null||bicycleName==""||bicycleName.length()<1){bicycleName = "";}

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
        model.addAttribute("bicycleName",bicycleName);
        model.addAttribute("bicycles", bicycles);
        model.addAttribute("page", bicyclePage);

        return "plan/item";
    }



    @GetMapping("/bicycle_detail")
    public String bicycle_detail(Model model,
                                 @RequestParam(value = "bicycleId") Integer bicycleId,
                                 HttpServletRequest request
    ){

        Bicycle savedBicycle = bicycleService.getBicycleById(bicycleId);
        BicycleDTO bicycleDTO = bicycleService.convertToDTO(bicycleService.getBicycleById(bicycleId));
        List<Recipe> recipe = recipeService.findByBicycle(savedBicycle);

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


        model.addAttribute("bicycle", savedBicycle);
        model.addAttribute("recipes", recipe);
        return "plan/bicycle_detail";
    }

    


    @GetMapping("/bicycle_insert")
    public String bicycle_insert(Model model,
                                 HttpServletRequest request){
        System.out.println("아이템생성");

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

        return "plan/bicycle_insert";
    }



    @PostMapping("/bicycle_save")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_A')")
    public String bicycle_save(@RequestBody BicycleDTO newBicycle) {
       Bicycle savedBicycle = bicycleService.saveBicycle(newBicycle);
        List<RecipeDTO> recipeList = newBicycle.getRecipes();
        if (recipeList != null) {
            for (RecipeDTO recipeDTO : recipeList) {
                RecipeId recipeId = new RecipeId();
                recipeId.setBicycleId(savedBicycle.getBicycleId());
                recipeId.setMaterial_name(recipeDTO.getMaterialName());
                Recipe newRecipe = Recipe.builder()
                        .id(recipeId)
                        .bicycle(savedBicycle)
                        .recipeCnt(recipeDTO.getRecipeCnt())
                        .build();
                recipeService.saveRecipe(newRecipe);
            }
        }


//         저장 후에는 다시 목록 화면으로 리다이렉트 또는 다른 작업 수행 가능
        return "redirect:/plan/item";
    }



    @PostMapping("/bicycle_delete")
    @ResponseBody
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_A')")
    public ResponseEntity<String> bicycle_delete(@RequestBody Integer bicycleId){

        try {
            List<Recipe> recipe = recipeService.findByBicycle(bicycleService.getBicycleById(bicycleId));
            if (recipe.isEmpty()) {
                bicycleService.deleteBicycle(bicycleId);
                return ResponseEntity.ok("삭제완료");

            } else {
                recipeService.deleteByBicycleId(bicycleId);
                bicycleService.deleteBicycle(bicycleId);
                return ResponseEntity.ok("삭제완료");

            }
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제실패");
        }

    }




    @PostMapping("/recipe_add")
    @ResponseBody
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_A')")
    public ResponseEntity<String> recipe_add (
            @RequestParam String previousCellValue,
            @RequestParam Integer bicycleIdValue
    ){

        RecipeId recipeId = new RecipeId(bicycleIdValue, previousCellValue);

        Recipe recipe =  new Recipe(recipeId,bicycleService.getBicycleById(bicycleIdValue),0);
        try {
            recipeService.saveRecipe(recipe);

            return ResponseEntity.ok("추가완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("추가실패");
        }

    }




    @PostMapping("/recipe_update")
    @ResponseBody
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_A')")
    public ResponseEntity<String> recipe_update(
            @RequestParam String previousCellValue,
            @RequestParam Integer previousInputValue,
            @RequestParam Integer bicycleIdValue
    ){
        System.out.println("previousCellValue : "+previousCellValue);
        System.out.println("previousInputValue :"+previousInputValue);
        System.out.println("bicycleIdValue :"+bicycleIdValue);

        RecipeId recipeId = new RecipeId(bicycleIdValue, previousCellValue);

        Recipe recipe =  new Recipe(recipeId,bicycleService.getBicycleById(bicycleIdValue),previousInputValue);

        try {
            recipeService.saveRecipe(recipe);

            return ResponseEntity.ok("수정완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("수정실패");
        }
    }

    


    @PostMapping("/recipe_delete")
    @ResponseBody
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_A')")
    public ResponseEntity<String> recipe_delete (
            @RequestParam(value="previousCellValue") String previousCellValue,
            @RequestParam(value="bicycleIdValue") Integer bicycleIdValue
    ){
        System.out.println("previousCellValue : "+previousCellValue);
        System.out.println("bicycleIdValue :"+bicycleIdValue);
        try {
            recipeService.deleteRecipe(bicycleIdValue, previousCellValue);
            return ResponseEntity.ok("삭제완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제실패");
        }
    }




    @GetMapping("/material")
    public String material(Model model,
                           @RequestParam(value="size", defaultValue = "10") int size,
                           @RequestParam(value="page", defaultValue = "0") int page,
                           @RequestParam(value = "materialName", required = false) String materialName,
                           @RequestParam(value = "materialCategory", defaultValue = "-1") Integer  materialCategory,
                           HttpServletRequest request
                          ){

            Page<MaterialDTO> materialPage = materialService.getAllMaterials(materialName, materialCategory, PageRequest.of(page, size, Sort.by(Sort.Order.desc("materialId"))));

        List<MaterialDTO> materials = materialPage.getContent();
        if(materialName==null||materialName==""||materialName.length()<1){materialName = "";}
        List<Code> codeList = materialService.findByCodeCategory("material_category");


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
        model.addAttribute("materialName", materialName);
        model.addAttribute("materials", materials);
        model.addAttribute("page", materialPage);
        model.addAttribute("code",codeList);

        model.addAttribute("category",materialCategory);
        System.out.println("codeList!!! : "+ codeList);

        return "plan/material";
    }
    @GetMapping("/material_detail")
    public String material_detail(
            Model model, @RequestParam(value = "materialId") Integer materialId,
            HttpServletRequest request){
        System.out.println("materialId : " + materialId);

        MaterialDTO materialDTO = materialService.getMaterialById(materialId);
        System.out.println(materialDTO);
        List<Code> codeList = materialService.findByCodeCategory("material_category");

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

        model.addAttribute("code",codeList);
        model.addAttribute("material", materialDTO);
        return "plan/material_detail";
    }

    @GetMapping("/material_insert")
    public String material_insert(Model model, HttpServletRequest request){

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


        return "plan/material_insert";
    }

    @PostMapping("/material_save")
    @ResponseBody
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_A')")
    public ResponseEntity<String> material_save(
            Model model,
            MaterialDTO materialDTO,
            HttpServletRequest request

    ){


        try {
            materialService.saveMaterial(materialDTO);
            return ResponseEntity.ok("품목등록성공");
        } catch (Exception e) {
            // 예외가 발생한 경우
            e.printStackTrace(); // 또는 로깅 라이브러리를 사용하여 로그에 기록할 수 있습니다.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("품목등록실패"); // 실패 시 메시지 반환
        }
//        CustomUserDetails userDetails = (CustomUserDetails) request.getSession().getAttribute("user");
//        Set<String> roles = userDetails.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.toSet());
//
//        UserDTO user = new UserDTO(userDetails.getUserId(),
//                userDetails.getUsername(),
//                userDetails.getName(),
//                userDetails.getEmail(),
//                userDetails.getPart(),
//                roles,
//                userDetails.getPhone());
//        model.addAttribute("user",user);
    }

    @PostMapping("/material_update")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_A')")
    @ResponseBody
    public ResponseEntity<String> material_update(
            MaterialDTO materialDTO
    ){
        System.out.println(materialDTO);
        try {
        materialService.saveMaterial(materialDTO);
            return ResponseEntity.ok("수정완료");
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("수정실패");
        }
    }

    @PostMapping("/material_delete")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_A')")
    @ResponseBody
    public ResponseEntity<String> material_delete (
            @RequestBody Integer materialId
    ){
        try {
            materialService.deleteMaterial(materialId);
            return ResponseEntity.ok("삭제완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제실패");
        }
    }

    @GetMapping("/item_search")
    public String item_search(Model model,
                              @RequestParam(value="size", defaultValue = "10") int size,
                              @RequestParam(value="page", defaultValue = "0") int page,
                              @RequestParam(value = "materialName", required = false) String materialName,
                              @RequestParam(value = "materialCategory", defaultValue = "-1") Integer  materialCategory,
                              HttpServletRequest request

    ){
        Page<MaterialDTO> materialPage = materialService.getAllMaterials(materialName, materialCategory, PageRequest.of(page, size, Sort.by(Sort.Order.desc("materialId"))));

        List<MaterialDTO> materials = materialPage.getContent();
        if(materialName==null||materialName==""||materialName.length()<1){materialName = "";}
        List<Code> codeList = materialService.findByCodeCategory("material_category");


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
        model.addAttribute("materialName", materialName);
        model.addAttribute("materials", materials);
        model.addAttribute("page", materialPage);
        model.addAttribute("code",codeList);

        model.addAttribute("category",materialCategory);
        System.out.println("codeList!!! : "+ codeList);
        return "plan/item_search";
    }

    @GetMapping("/material_search/{materialId}")
    @ResponseBody
    public MaterialDTO getMaterialById(@PathVariable(value = "materialId") int materialId) {

        return materialService.getMaterialById(materialId);
    }



    @GetMapping("/quotation")
    public String quotation(Model model,
                            @RequestParam(value="size", defaultValue = "10") int size,
                            @RequestParam(value="page", defaultValue = "0") int page,
                            @RequestParam(value="category", defaultValue = "-1") int category,
                            @RequestParam(value="keyword", defaultValue = "") String keyword,
                            @RequestParam(name = "startDate", defaultValue = "2020-01-01T13:00:00")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                            @RequestParam(name = "endDate", defaultValue = "2030-01-01T13:00:00")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime endDate,
                            HttpServletRequest request
                            ){
        System.out.println("견적서접속요청");

        Page<QuotationDTO> QuotationPage = quotationService.quotationList(keyword, category,startDate, endDate ,PageRequest.of(page, size,Sort.by(Sort.Order.desc("id"))));
        List<QuotationDTO> quotationDetailDTOList = QuotationPage.getContent();

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

        List<Code> codeList = materialService.findByCodeCategory("quotation_status");
        model.addAttribute("code", codeList);
        model.addAttribute("page", QuotationPage);
        model.addAttribute("quotations", quotationDetailDTOList);
        model.addAttribute("category",category);
        model.addAttribute("keyword",keyword);
        model.addAttribute("startDate",startDate);
        model.addAttribute("endDate",endDate);
        System.out.println("quotationDTO :" + quotationDetailDTOList);


        return "plan/quotation";
    }

    @GetMapping("/quotationDetail")
    public String quotationDetail(
            Model model, @RequestParam(value = "quotationId") String quotationId,
            HttpServletRequest request){
        System.out.println("quotationId : " + quotationId);

        Quotation quotation = quotationService.findById(quotationId);

        QuotationDTO quotationDTO = quotationService.convertToDTO(quotation);

        List<QuotationDetailDTO> quotationDetailDTOList = quotationService.findAllByQuotation(quotation);
        System.out.println("quotationDTO :" + quotationDTO);
        System.out.println("quotationDetailDTOList :"+quotationDetailDTOList);





        List<Code> codeList = materialService.findByCodeCategory("quotation_status");

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
        model.addAttribute("code",codeList);
        model.addAttribute("quotation", quotationDTO);
        model.addAttribute("quotationDetailDTOList", quotationDetailDTOList);

        return "plan/quotation_detail";
    }


    @PostMapping("/save_quotation")
    @ResponseBody
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_A')")
    public String saveQuotation(@RequestBody QuotationDTO quotationDTO) {
        quotationService.saveQuotation(quotationDTO);
        return "견적서저장";
    }

    @PostMapping("/quotation_accept")
    @ResponseBody
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_A')")
    public String quotation_accept(@RequestBody String Id) {
        System.out.println(Id);

        try {
            Quotation quotation = quotationService.findById(Id);
            QuotationDTO quotationDTO = quotationService.convertToDTO(quotation);
            quotationDTO.setStatus(2);
            quotationService.saveQuotation(quotationDTO);

            return "견적서 승인 성공";
        } catch (Exception e) {
            // 삭제 중 에러가 발생한 경우 처리
            return "견적서 승인 실패: " + e.getMessage();
        }
    }

    @PostMapping("/quotation_drop")
    @ResponseBody
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_A')")
    public String quotation_drop(@RequestBody String Id) {
        System.out.println(Id);

        try {
            Quotation quotation = quotationService.findById(Id);
            QuotationDTO quotationDTO = quotationService.convertToDTO(quotation);
            quotationDTO.setStatus(1);
            quotationService.saveQuotation(quotationDTO);
            return "견적서 Drop 성공";
        } catch (Exception e) {
            // 삭제 중 에러가 발생한 경우 처리
            return "견적서 Drop 실패: " + e.getMessage();
        }
    }

    @PostMapping("/quotation_delete")
    @ResponseBody
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_A')")
    public String quotation_delete(@RequestBody String Id) {
        System.out.println(Id);

        try {
            // 여기에 ID를 이용한 삭제 로직 추가
            quotationService.deleteQuotation(Id);
            return "견적서 삭제 성공";
        } catch (Exception e) {
            // 삭제 중 에러가 발생한 경우 처리
            return "견적서 삭제 실패: " + e.getMessage();
        }
    }

    @GetMapping("/quotation_insert")
    public String documentInsert(Model model, HttpServletRequest request){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedDate = now.format(formatter);
        String quotationId = formattedDate.toString() +"00"+ new Random().nextInt(1000);

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

        model.addAttribute("quotationId",quotationId);
        return "plan/quotation_insert";
    }

    @GetMapping("/company_search")
    public String company_search(Model model, HttpServletRequest request,
                                 @RequestParam(value = "size", defaultValue = "10") int size,
                                 @RequestParam(value ="page" ,defaultValue = "0") int page,
                                 @RequestParam(value = "keyword", required = false) String keyword) {
        Page<CompanyListViewResponse> companyList = companyService.companyList(size, page,keyword);

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

        model.addAttribute("list", companyList);
        return "plan/company_search";
    }

    @GetMapping("/procurementPlan")
    public String procurementPlan(Model model,
                            @RequestParam(value="size", defaultValue = "10") int size,
                            @RequestParam(value="page", defaultValue = "0") int page,
                            @RequestParam(value="category", defaultValue = "-1") int category,
                            @RequestParam(value="keyword", defaultValue = "") String keyword,
                            @RequestParam(name = "startDate", defaultValue = "2020-01-01T13:00:00")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                            @RequestParam(name = "endDate", defaultValue = "2030-01-01T13:00:00")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime endDate,
                            HttpServletRequest request
    ){
        System.out.println("견적서접속요청");

        Page<DocumentDTO> dtoPage = documentService.procurementList(keyword, category,startDate, endDate ,PageRequest.of(page, size,Sort.by(Sort.Order.desc("id"))));
        List<DocumentDTO> documentDTOList = dtoPage.getContent();

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

        List<Code> codeList = materialService.findByCodeCategory("quotation_status");
        model.addAttribute("code", codeList);
        model.addAttribute("page", dtoPage);
        model.addAttribute("procurements", documentDTOList);
        model.addAttribute("category",category);
        model.addAttribute("keyword",keyword);
        model.addAttribute("startDate",startDate);
        model.addAttribute("endDate",endDate);
        System.out.println("quotationDTO :" + documentDTOList);


        return "plan/procurementPlan";
    }


    @GetMapping("/procurementPlan_insert")
    public String procurementPlan_insert(Model model, HttpServletRequest request){
        System.out.println("조달계획서생성");

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

        return "plan/procurementPlan_insert";
    }



    @GetMapping("/document")
    public String document(Model model,
                           HttpServletRequest request){
        List<DocumentDTO> documents = documentService.getAllDocuments();

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
        model.addAttribute("documents", documents);
        return "plan/document";
    }

    @GetMapping("/document/{documentId}")
    public String getDocumentWithDetails(
            @PathVariable("documentId")String documentId,
            HttpServletRequest request,
            Model model
    ){
        DocumentDTO documents = documentService.findDocumentById(documentId);
        List<DocumentDetail> documentDetails = documentService.findDocumentDetailsByDocumentId(documentId);

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
        model.addAttribute("documents", documents);
        model.addAttribute("documentDetails", documentDetails);
        return "plan/document_detail_list";
    }


    @GetMapping("/searchCompany")
    public String searchCompany(@RequestParam("name") String companyName, Model model) {
        // 여기서 companyName을 사용하여 MySQL 데이터베이스에서 회사 정보를 검색
        // 예를 들어, CompanyService를 통해 데이터베이스에서 정보를 가져온다고 가정
       System.out.println(companyName);



            // 검색된 회사 정보를 모델에 추가하여 HTML로 반환
         //   model.addAttribute("company", company);
            return "plan/company_search"; // 회사 정보 템플릿 이름

    }

}
