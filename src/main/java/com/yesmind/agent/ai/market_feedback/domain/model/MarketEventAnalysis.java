package com.yesmind.agent.ai.market_feedback.domain.model;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketEventAnalysis {
    private String id;
    private String sourceId;
    private String theme;
    private String type;
    private LocalDateTime genereLe;
    private String prediction;
    private String proposition;
    private String ton;
    private String urgence;
    private String categorie;
    private LocalDateTime analyseEl;
}