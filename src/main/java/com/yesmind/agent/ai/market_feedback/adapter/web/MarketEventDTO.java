package com.yesmind.agent.ai.market_feedback.adapter.web;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

// Objet de transfert — ce qu'on envoie au frontend (pas le modèle domaine)
@Getter
@Builder
public class MarketEventDTO {

    private String id;
    private String content;
    private String sourceUrl;
    private String sourceType;
    private LocalDateTime creationDate;
}