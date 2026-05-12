package com.yesmind.agent.ai.market_feedback.domain.model;

import lombok.*;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class MarketEventSummaryFilter {
    private String search;  // cherche dans famille OU themes[].theme OU themes[].contenu_fr/en
    private int page;
    private int size;
}