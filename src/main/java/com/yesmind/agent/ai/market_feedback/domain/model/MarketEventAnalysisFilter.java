package com.yesmind.agent.ai.market_feedback.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketEventAnalysisFilter {
    private String search;
    private String famille;
    private String urgence;
    private int page;
    private int size;
}