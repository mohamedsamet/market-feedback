package com.yesmind.agent.ai.market_feedback.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketEventAnalysisFilter {
    private String search;
    private String urgence;
    private String famille;
    private int page;
    private int size;
}