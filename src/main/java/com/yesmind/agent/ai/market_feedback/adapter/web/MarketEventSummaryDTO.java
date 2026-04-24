package com.yesmind.agent.ai.market_feedback.adapter.web;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Builder
public class MarketEventSummaryDTO {
    private String id;
    private String theme;
    private int nombreArticles;
    private LocalDateTime genereLe;
    private String type;
    private String contenuFr;
    private String contenuEn;
    private int pageDB;
    private int totalPagesDB;
}