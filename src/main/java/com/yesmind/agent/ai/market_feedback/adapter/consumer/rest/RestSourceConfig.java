package com.yesmind.agent.ai.market_feedback.adapter.consumer.rest;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

// Représente la configuration d'une seule source REST
@Getter
@Setter
public class RestSourceConfig {

    private String url;

    private String apiKey;

    private String method;

    private boolean enabled;
    private String query;

    private int timeout;

    private String description;
    private int daysBack;

    private int retry;
    public String getFromDate() {
        return LocalDate.now().minusDays(daysBack).toString();
    }

}