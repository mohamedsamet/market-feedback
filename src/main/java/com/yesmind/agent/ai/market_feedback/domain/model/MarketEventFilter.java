package com.yesmind.agent.ai.market_feedback.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketEventFilter {
    private String search;
    private int page;
    private int size;
}