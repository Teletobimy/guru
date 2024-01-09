package com.ezen.guru.service.purchase;

import com.ezen.guru.domain.PurchaseOrder;
import com.ezen.guru.domain.PurchaseOrderDetail;

import java.util.List;

public interface OrderService {

    public List<PurchaseOrder> getProductBeforeOrderDetailsByStatus(int category);

    public List<PurchaseOrder> getProductAfterOrderDetailsByStatus();

    public List<PurchaseOrder> getProductDeliveringOrderDetailsByStatus();

    public List<PurchaseOrder> getProductClosedOrderDetailsByStatus();

    public List<PurchaseOrderDetail> getPurchaseOrderDocument(String id);

}
