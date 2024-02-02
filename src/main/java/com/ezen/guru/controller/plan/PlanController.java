package com.ezen.guru.controller.plan;



import com.ezen.guru.config.RecipeId;
import com.ezen.guru.domain.*;
import com.ezen.guru.dto.UserDTO;
import com.ezen.guru.dto.plan.*;
import com.ezen.guru.dto.purchase.CompanyListViewResponse;
import com.ezen.guru.dto.purchase.PurchaseOrderDTO;
import com.ezen.guru.dto.purchase.PurchaseOrderDetailDTO;
import com.ezen.guru.service.CustomUserDetails;
import com.ezen.guru.service.plan.*;
import com.ezen.guru.service.purchase.CompanyService;
import com.ezen.guru.service.purchase.OrderService;
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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
    private final OrderService orderService;


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
            @RequestParam(value = "previousCellValue") String previousCellValue,
            @RequestParam(value = "bicycleIdValue") Integer bicycleIdValue
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
            @RequestParam(value = "previousCellValue") String previousCellValue,
            @RequestParam(value = "previousInputValue") Integer previousInputValue,
            @RequestParam(value = "bicycleIdValue") Integer bicycleIdValue
    ){


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


        return "plan/material";
    }
    @GetMapping("/material_detail")
    public String material_detail(
            Model model, @RequestParam(value = "materialId") Integer materialId,
            HttpServletRequest request){


        MaterialDTO materialDTO = materialService.getMaterialById(materialId);

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
        Integer biddingNo;
        try {
            biddingNo = Integer.valueOf(keyword);

        } catch (NumberFormatException e) {
            biddingNo=null;
        }
        Page<QuotationDTO> QuotationPage = quotationService.quotationList(biddingNo, category,startDate, endDate ,PageRequest.of(page, size,Sort.by(Sort.Order.desc("id"))));
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



        return "plan/quotation";
    }

    @GetMapping("/quotationDetail")
    public String quotationDetail(
            Model model, @RequestParam(value = "quotationId") String quotationId,
            HttpServletRequest request){


        Quotation quotation = quotationService.findById(quotationId);

        QuotationDTO quotationDTO = quotationService.convertToDTO(quotation);

        List<QuotationDetailDTO> quotationDetailDTOList = quotationService.findAllByQuotation(quotation);






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


        try {
            Quotation quotation = quotationService.findById(Id);
            QuotationDTO quotationDTO = quotationService.convertToDTO(quotation);

                List<QuotationDTO> quotations = quotationService.findAllByBiddingNo(quotationDTO.getBiddingNo());
                for (int i = 0; i < quotations.size(); i++) {
                    quotations.get(i).setStatus(1);
                    quotationService.saveQuotation(quotations.get(i));
                }

            quotationDTO.setStatus(2);
            System.out.println(quotationDTO);
            quotationService.saveQuotation(quotationDTO);






            DocumentDTO documentDTO = new DocumentDTO();
            documentDTO.setType(1);
            documentDTO.setId(quotationDTO.getId());
            documentDTO.setBiddingNo(quotationDTO.getBiddingNo());
            documentDTO.setCompany(quotationDTO.getCompany());
            documentDTO.setDocumentTotalPrice(quotationDTO.getQuotation_totalprice());
            documentDTO.setDeadline(quotationDTO.getDeadline());
            documentDTO.setDocumentMemo(quotationDTO.getQuotation_memo());
            documentDTO.setLeadTime(quotationDTO.getLeadTime());
            documentDTO.setPaymentTerms(quotationDTO.getPaymentTerms());
            documentDTO.setTradeTerms(quotationDTO.getTradeTerms());


            List<DocumentDetail> documentDetailList = quotationDTO.getQuotationDetails()
                    .stream()
                    .map(quotationDetailDTO -> {
                        DocumentDetail documentDetail = new DocumentDetail();
                        documentDetail.setDocument(documentService.convertToDocument(documentDTO));
                        documentDetail.setMaterial(quotationDetailDTO.getMaterial());
                        documentDetail.setMaterialName(quotationDetailDTO.getMaterialName());
                        documentDetail.setDocumentCnt(quotationDetailDTO.getQuotationCnt());
                        documentDetail.setDocumentMeasure(quotationDetailDTO.getQuotationMeasure());
                        documentDetail.setDocumentPrice(quotationDetailDTO.getQuotationPrice());
                        return documentDetail;
                    })
                    .collect(Collectors.toList());

            documentDTO.setDocumentDetails(documentDetailList);
            documentService.documentSave(documentDTO);



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

        List<Code> codeList = materialService.findByCodeCategory("document_status");
        model.addAttribute("code", codeList);
        model.addAttribute("page", dtoPage);
        model.addAttribute("procurements", documentDTOList);
        model.addAttribute("category",category);
        model.addAttribute("keyword",keyword);
        model.addAttribute("startDate",startDate);
        model.addAttribute("endDate",endDate);



        return "plan/procurementPlan";
    }


    @GetMapping("/procurementPlan_insert")
    public String procurementPlan_insert(Model model, HttpServletRequest request){
        LocalDateTime now = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    String formattedDate = now.format(formatter);
    String documentId = formattedDate.toString() +"00"+ new Random().nextInt(1000);

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

        model.addAttribute("documentId",documentId);

        return "plan/procurementPlan_insert";
    }



    //procurementPlan_save
    @PostMapping("/procurementPlan_save")
    @ResponseBody
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_A')")
    public void procurementPlan_save(@RequestBody DocumentDTO documentDTO) {
        documentService.documentSave(documentDTO);

    }

    @PostMapping("/procurement_trans")
    @ResponseBody
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_A')")
    public String procurement_trans(@RequestBody String Id) {


        try {

            DocumentDTO documentDTO = documentService.findDocumentById(Id);
            documentDTO.setStatus(1);
            documentService.documentSave(documentDTO);

            return "발주 성공";
        } catch (Exception e) {
            // 삭제 중 에러가 발생한 경우 처리
            return "발주 실패: " + e.getMessage();
        }
    }

    @PostMapping("/procurement_delete")
    @ResponseBody
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_A')")
    public String procurement_delete(@RequestBody String Id) {


        try {
            // 여기에 ID를 이용한 삭제 로직 추가
            documentService.documentDelete(Id);
            return "조달계획서 삭제 성공";
        } catch (Exception e) {
            // 삭제 중 에러가 발생한 경우 처리
            return "조달계획서 삭제 실패: " + e.getMessage();
        }
    }


    @GetMapping("/procurementPlan_detail")
    public String procurementPlan_detail(
            Model model, @RequestParam(value = "procurementId") String procurementId,
            HttpServletRequest request){



        DocumentDTO documentdto = documentService.findDocumentById(procurementId);
        List<DocumentDetail> documentDetailList = documentdto.getDocumentDetails();

        List<Code> codeList = materialService.findByCodeCategory("document_status");

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
        model.addAttribute("document", documentdto);
        model.addAttribute("documentDetailList", documentDetailList);

        return "plan/procurementPlan_detail";
    }


    @GetMapping("/document")
    public String document(Model model,
                           @RequestParam(value="size", defaultValue = "10") int size,
                           @RequestParam(value="page", defaultValue = "0") int page,
                           @RequestParam(value="category", defaultValue = "-1") int category,
                           @RequestParam(value="keyword", defaultValue = "") String keyword,
                           @RequestParam(name = "startDate", defaultValue = "2020-01-01T13:00:00")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                           @RequestParam(name = "endDate", defaultValue = "2030-01-01T13:00:00")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime endDate,
                           HttpServletRequest request){

        Page<DocumentDTO> dtoPage = documentService.documentList(keyword, category,startDate, endDate ,PageRequest.of(page, size,Sort.by(Sort.Order.desc("id"))));
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

        List<Code> codeList = materialService.findByCodeCategory("document_status");

        model.addAttribute("code", codeList);
        model.addAttribute("page", dtoPage);
        model.addAttribute("documents", documentDTOList);
        model.addAttribute("category",category);
        model.addAttribute("keyword",keyword);
        model.addAttribute("startDate",startDate);
        model.addAttribute("endDate",endDate);



        return "plan/document";
    }


    @GetMapping("/document_detail")
    public String document_detail(
            Model model, @RequestParam(value = "documentId") String documentId,
            HttpServletRequest request){



        DocumentDTO documentdto = documentService.findDocumentById(documentId);
        List<DocumentDetail> documentDetailList = documentdto.getDocumentDetails();
        int biddingNo = documentdto.getBiddingNo();
        List<QuotationDTO> quotationDTOList = quotationService.findAllByBiddingNo(biddingNo);

        System.out.println(quotationDTOList);
        List<Code> codeList = materialService.findByCodeCategory("document_status");

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
        List<Code> codeList2 = materialService.findByCodeCategory("quotation_status");
        model.addAttribute("code2", codeList2);
        model.addAttribute("user",user);
        model.addAttribute("quotations",quotationDTOList);
        model.addAttribute("code",codeList);
        model.addAttribute("document", documentdto);
        model.addAttribute("documentDetailList", documentDetailList);

        return "plan/document_detail";
    }

    @PostMapping("/document_trans")
    @ResponseBody
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_A')")
    public String document_trans(@RequestBody String Id) {


        try {

            DocumentDTO documentDTO = documentService.findDocumentById(Id);
            documentDTO.setStatus(1);
            documentService.documentSave(documentDTO);

            return "발주 성공";
        } catch (Exception e) {
            // 삭제 중 에러가 발생한 경우 처리
            return "발주 실패: " + e.getMessage();
        }
    }

    @PostMapping("/document_delete")
    @ResponseBody
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_A')")
    public String document_delete(@RequestBody String Id) {


        try {
            // 여기에 ID를 이용한 삭제 로직 추가
            documentService.documentDelete(Id);
            return "계약서 삭제 성공";
        } catch (Exception e) {
            // 삭제 중 에러가 발생한 경우 처리
            return "계약서 삭제 실패: " + e.getMessage();
        }
    }



    @GetMapping("/po_ready")
    public String po_ready(
            Model model, @RequestParam(value = "documentId") String documentId,
            HttpServletRequest request){



        DocumentDTO documentdto = documentService.findDocumentById(documentId);
        List<DocumentDetail> documentDetailList = documentdto.getDocumentDetails();

        List<Code> codeList = materialService.findByCodeCategory("document_status");

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
        model.addAttribute("document", documentdto);
        model.addAttribute("documentDetailList", documentDetailList);

        return "plan/po_ready";
    }


    @PostMapping("/purchaseOrder_save")
    @ResponseBody
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_A')")
    public ResponseEntity<String> purchaseOrder_save(@RequestBody PurchaseOrderDTO purchaseOrderDTO) {


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
        String currentDate = dateFormat.format(new Date());

        // 4자리의 랜덤 숫자 생성
        Random random = new Random();
        int randomNum = random.nextInt(1000); // 0부터 9999까지의 숫자 중에서 랜덤

        // 랜덤 아이디 조합
        String randomId = currentDate + String.format("%04d", randomNum);


        DocumentDTO document = documentService.findDocumentById(purchaseOrderDTO.getDocumentId());
        System.out.println("document : "+document);
        Document documentchange = documentService.convertToDocument(document);
        System.out.println("documentchange : "+documentchange);

        int totalprice = document.getDocumentTotalPrice();
        String memo = document.getDocumentMemo();
        LocalDate localDate = LocalDate.parse(purchaseOrderDTO.getDeadline());
        LocalDateTime deadline = localDate.atTime(LocalTime.MIN);

        LocalDateTime regdate = LocalDateTime.now();
        PurchaseOrder purchaseOrder = new PurchaseOrder();

        purchaseOrder.setId(randomId);
        purchaseOrder.setDocument(documentchange);
        purchaseOrder.setCompany(document.getCompany());
        purchaseOrder.setTotalprice(totalprice);
        purchaseOrder.setRegdate(regdate);
        purchaseOrder.setStatus(1);
        purchaseOrder.setMemo(memo);
        purchaseOrder.setDeadline(deadline);
        purchaseOrder.setLeadTime(document.getLeadTime());
        purchaseOrder.setTradeTerms(document.getTradeTerms());
        purchaseOrder.setPaymentTerms(document.getPaymentTerms());
        PurchaseOrder savedOrder = orderService.saveOrder(purchaseOrder);

        List<PurchaseOrderDetailDTO> purchaseOrderDetails = purchaseOrderDTO.getPurchaseOrderDetailDTOList();

        List<PurchaseOrderDetail> Detaillist = new ArrayList<>();
        int total=0;
        for (PurchaseOrderDetailDTO detailDTO : purchaseOrderDetails) {

            MaterialDTO material = materialService.getMaterialById(detailDTO.getMaterialId());
            Material materialEntity = materialService.convertToEntity(material);

            PurchaseOrderDetail purchaseOrderDetail = PurchaseOrderDetail.builder()
                    .purchaseOrderCnt(detailDTO.getPurchaseOrderCnt())
                    .materialName(material.getMaterialName())
                    .materialCategory(material.getMaterialCategory())
                    .materialMeasure(material.getMaterialMeasure())
                    .materialPrice(material.getMaterialPrice())
                    .check(detailDTO.getCheck())
                    .purchaseOrder(savedOrder)
                    .material(materialEntity)
                    .qcCheckCnt(detailDTO.getQcCheckCnt())
                    .build();
            orderService.saveDetailOrder(purchaseOrderDetail);
            total = (detailDTO.getPurchaseOrderCnt()*material.getMaterialPrice())+total;
        }

        savedOrder.setTotalprice(total);
        orderService.saveOrder(savedOrder);



        return ResponseEntity.ok("전송완료");
    }


}
