package com.yesmind.agent.ai.market_feedback.domain.model;

import lombok.*;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PagedResult<T> {
    private List<T> content;
    private long totalElements;
    private int totalPages;
    private int currentPage;
}