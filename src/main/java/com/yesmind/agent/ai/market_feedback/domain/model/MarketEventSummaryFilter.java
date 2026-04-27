package com.yesmind.agent.ai.market_feedback.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketEventSummaryFilter {
    private String search;   // cherche dans theme OU contenu_fr OU contenu_en
    private int page;
    private int size;
}