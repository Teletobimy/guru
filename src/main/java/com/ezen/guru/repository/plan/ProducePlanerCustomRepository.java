package com.ezen.guru.repository.plan;

import com.ezen.guru.domain.ProducePlaner;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface ProducePlanerCustomRepository {

    Page<ProducePlaner> producePlanerList(int size, int page, int category, String keyword, LocalDateTime startDate, LocalDateTime endDate);

}
