package com.yesmind.agent.ai.market_feedback.adapter.web;

import lombok.*;
import java.util.List;
//version exposé au front:même structure que PagedResult mais dans la couche web
@Getter
@Builder
@AllArgsConstructor
public class PagedResultDTO<T> {
    private List<T> content;//10 elemnts par page
    private long totalElements; //total/10
    private int totalPages;
    private int currentPage;
}