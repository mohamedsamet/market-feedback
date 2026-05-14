package com.yesmind.agent.ai.market_feedback.domain.model;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketEventAnalysis {

    private String id;
    private String famille;
    private LocalDateTime genereLe;
    private Integer totalThemes;
    private Integer pageDB;
    private Integer totalPagesDB;
    private LocalDateTime analyseLe;
    private List<ThemeAnalysis> themes;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ThemeAnalysis {
        private String theme;
        private String prediction;
        private Map<String, String> propositions;
        private String ton;
        private String urgence;
        private String categorie;
        private LocalDateTime analyseLe;
    }
}