package com.yesmind.agent.ai.market_feedback.domain.model;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketEventSummary {
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