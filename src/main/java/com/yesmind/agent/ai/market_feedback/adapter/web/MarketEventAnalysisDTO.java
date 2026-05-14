package com.yesmind.agent.ai.market_feedback.adapter.web;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Builder
public class MarketEventAnalysisDTO {

    private String id;
    private String famille;
    private LocalDateTime genereLe;
    private Integer totalThemes;
    private Integer pageDB;
    private Integer totalPagesDB;
    private LocalDateTime analyseLe;
    private List<ThemeAnalysisDTO> themes;

    @Getter
    @Builder
    public static class ThemeAnalysisDTO {
        private String theme;
        private String prediction;
        private Map<String, String> propositions; // {"0": "...", "1": "...", "2": "..."}
        private String ton;
        private String urgence;
        private String categorie;
        private LocalDateTime analyseLe;
    }
}