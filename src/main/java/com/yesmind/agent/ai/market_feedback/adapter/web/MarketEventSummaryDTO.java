package com.yesmind.agent.ai.market_feedback.adapter.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class MarketEventSummaryDTO {
    private String id;
    private String famille;
    private LocalDateTime genereLe;
    private int pageDB;
    private int totalPagesDB;
    private List<ThemeSummaryDTO> themes;

    @Getter
    @Builder
    public static class ThemeSummaryDTO {
        private String theme;

        @JsonProperty("contenuFr")   // ← force le nom en JSON
        private String contenuFr;

        @JsonProperty("contenuEn")
        private String contenuEn;
    }
}