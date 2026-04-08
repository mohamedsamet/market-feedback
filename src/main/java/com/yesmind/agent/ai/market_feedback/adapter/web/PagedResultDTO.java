package com.yesmind.agent.ai.market_feedback.adapter.web;

import lombok.*;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PagedResultDTO<T> {
    private List<T> content;
    private long totalElements;
    private int totalPages;
    private int currentPage;
}