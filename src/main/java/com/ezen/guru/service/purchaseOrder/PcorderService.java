package com.ezen.guru.service.purchaseOrder;

import com.ezen.guru.domain.PurchaseOrderDetail;
import com.ezen.guru.repository.purchaseOrder.PcorderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PcorderService {

    private final PcorderRepository pcorderRepository;

    public List<PurchaseOrderDetail> findAll() {
        return pcorderRepository.findAll();
    }
}
