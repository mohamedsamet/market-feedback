package com.yesmind.agent.ai.market_feedback.adapter.web;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class MarketEventAnalysisDTO {
    private String id;
    private String sourceId;
    private String theme;
    private String type;
    private LocalDateTime genereLe;
    private String prediction;
    private List<String> propositions;
    private String ton;
    private String urgence;
    private String categorie;
    private LocalDateTime analyseEl;
}