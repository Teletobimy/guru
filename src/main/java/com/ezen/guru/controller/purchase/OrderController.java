package com.ezen.guru.controller.purchase;

import com.ezen.guru.domain.*;
import com.ezen.guru.dto.purchase.*;
import com.ezen.guru.service.purchase.CompanyService;
import com.ezen.guru.service.purchase.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RequiredArgsConstructor
@RequestMapping("/purchase")
@Controller
public class OrderController {

    private final OrderService orderService;
    private final CompanyService companyService;

    @GetMapping("/order")
    public String getOrderList(Model model,
                               @RequestParam(value = "size", defaultValue = "10") int size,
                               @RequestParam(value ="page" ,defaultValue = "0") int page,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "category", defaultValue = "-1") int category,
                               String id) {
        Page<OrderListViewResponse> orderList = orderService.orderList(size, page,keyword,category);
        List<Code> codeList = orderService.findByCodeCategory("purchase_order_status");
        orderService.closeOrder(id);

        model.addAttribute("list", orderList);
        model.addAttribute("code",codeList);
        model.addAttribute("category",category);
        return "purchase/order";
    }
    @GetMapping("/order_detail")
    public String getDetail(Model model, @RequestParam String id) {
        List<OrderDetailViewResponse> list = orderService.getPurchaseOrderDetail(id);
        List<Code> codeList = orderService.findByCodeCategory("material_category");
        List<Code> typeCode = orderService.findByCodeCategory("document_type");

        model.addAttribute("code",codeList);
        model.addAttribute("type", typeCode);
        model.addAttribute("list", list);
        return "purchase/order_detail";
    }
    @PostMapping("/order/shipment")
    public ResponseEntity<String> addToShipment(@RequestBody List<AddShipmentRequest> shipments) {
//        List<AddShipmentRequest> savedEntities = new ArrayList<>();
//        for (Shipment shipment : shipments) {
//            savedEntities.add(orderService.saveToShipment(shipment));
//        return new ResponseEntity<>("Entities added successfully", HttpStatus.OK);
//        }
        orderService.saveToShipment(shipments);
        return new ResponseEntity<>("Entities added successfully", HttpStatus.OK);
    }
    @GetMapping("/order_print")
    public String getPrint(Model model, @RequestParam String id) {
        List<OrderPrintViewResponse> list = orderService.getPurchaseOrderPrint(id);
                /*orderService.getPurchaseOrderPrint(id).stream()
                .map(OrderPrintViewResponse::new)
                .toList();*/
        List<Code> codeList = orderService.findByCodeCategory("material_category");

        model.addAttribute("code",codeList);
        model.addAttribute("list", list);

        return "purchase/order_print";
    }
    @GetMapping("/company_list")
    public String getOrderList(Model model,
                               @RequestParam(value = "size", defaultValue = "10") int size,
                               @RequestParam(value ="page" ,defaultValue = "0") int page,
                               @RequestParam(value = "keyword", required = false) String keyword) {
        Page<CompanyListViewResponse> companyList = companyService.companyList(size, page,keyword);
//        List<CompanyListViewResponse> list = companyService.getCompanyList().stream()
//                .map(CompanyListViewResponse::new)
//                .toList();
        model.addAttribute("list", companyList);
        return "purchase/company_list";
    }
    @PostMapping("/company/add")
    public ResponseEntity<String> addToCompany(@RequestBody AddCompanyRequest company) {
        Company newCompany = companyService.newCompany(company);
        return new ResponseEntity<>("협력사가 등록되었습니다.", HttpStatus.OK);
    }


//    미발주 계약 및 조달 건들의 번호, 회사명, 제목, 날짜를 목록으로 보여주고
//    상세 버튼을 누르면 상세 내역 열람하는 페이지로 이동
//    그 후 발주 버튼을 누르면 상세 내역(과 비교하며 입력할 수 있는)을 확인할 수 있는 (폼이 있는) 모달 창
//    입력 후 완료 버튼 누르면 유효성 검사 실행, 성공하면 DB에서 발주 상태로 바뀜
//    필요한 발주를 완료한 후 상세 페이지에서 목록 버튼 누르면 화면으로 redirect

//    발주 완료된 발주서 목록을 보여주고
//    상세 버튼을 누르면 trade.html 형태의 발주서 열람하는 페이지로 이동
//    끝! 단순 조회 페이지 (수정, 삭제 불가능?)
//    +) 검수 일정 입력하러 가는 페이지(jquery datepicker_예정)

//    발주가 마감된 품목의 목록을 띄워줍니다
//    클릭하면 발주서 조회

}
