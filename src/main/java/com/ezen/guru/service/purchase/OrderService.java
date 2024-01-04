package com.ezen.guru.service.purchase;

import com.ezen.guru.domain.PurchaseOrderDetail;

import java.util.List;

public interface OrderService {

    public List<PurchaseOrderDetail> getProductOrderDetailsByPurchaseOrderStatus();


}
