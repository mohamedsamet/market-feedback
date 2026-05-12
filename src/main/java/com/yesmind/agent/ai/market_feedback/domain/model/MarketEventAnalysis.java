package com.yesmind.agent.ai.market_feedback.domain.model;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketEventAnalysis {
    private String id;
    private String famille;
    private LocalDateTime genereLe;
    private int totalThemes;
    private int pageDB;
    private int totalPagesDB;
    private LocalDateTime analyseEl;
    private List<Theme> themes;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Theme {
        private String sourceId;
        private String theme;
        private String prediction;
        private String proposition;
        private String ton;
        private String urgence;
        private String categorie;
        private LocalDateTime analyseEl;
    }
}