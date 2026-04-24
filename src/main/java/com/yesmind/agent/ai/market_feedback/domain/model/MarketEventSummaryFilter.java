package com.yesmind.agent.ai.market_feedback.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketEventSummaryFilter {
    private String search;   // cherche dans theme ou contenu
    private String theme;     // filtre par theme (RSS, etc.)
    private int page;
    private int size;
}