package com.ezen.guru.service.receive;

import com.ezen.guru.dto.receive.TradeDTO;
import com.ezen.guru.dto.receive.TradeDetailDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface TradeService {
    public Page<TradeDTO> tradeList(Pageable pageable, LocalDateTime startDate, LocalDateTime endDate);
    public List<TradeDetailDTO> tradeDetailList(String purchaseOrderId);
}
