package com.ezen.guru.service.receive;

import com.ezen.guru.dto.receive.TradeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface TradeService {
    public Page<TradeDTO> tradeList(Pageable pageable, LocalDateTime startDate, LocalDateTime endDate);
}
