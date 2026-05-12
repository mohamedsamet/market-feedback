package com.yesmind.agent.ai.market_feedback.domain.model;

import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class MarketEventSummary {

    private String id;
    private String famille;
    private LocalDateTime genereLe;
    private int pageDB;
    private int totalPagesDB;
    private List<ThemeSummary> themes;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class ThemeSummary {
        private String theme;
        private String contenuFr;
        private String contenuEn;
    }
}