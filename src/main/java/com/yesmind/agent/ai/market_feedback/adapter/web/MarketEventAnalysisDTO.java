package com.yesmind.agent.ai.market_feedback.adapter.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class MarketEventAnalysisDTO {
    private String id;
    private String famille;

    @JsonProperty("genereLe")
    private LocalDateTime genereLe;

    @JsonProperty("totalThemes")
    private int totalThemes;

    private int pageDB;
    private int totalPagesDB;

    @JsonProperty("analyseEl")
    private LocalDateTime analyseEl;

    private List<ThemeDTO> themes;

    @Getter
    @Builder
    public static class ThemeDTO {
        private String theme;
        private String prediction;
        private String proposition;
        private String ton;
        private String urgence;
        private String categorie;

        @JsonProperty("analyseEl")
        private LocalDateTime analyseEl;
    }
}