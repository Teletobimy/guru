package com.ezen.guru.repository.plan;

import com.ezen.guru.domain.ProducePlaner;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProducePlanerCustomRepository {

    Page<ProducePlaner> producePlanerList(int page, int size, int category, String keyword);

}
