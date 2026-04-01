package com.yesmind.agent.ai.market_feedback.adapter.consumer.rest;

import lombok.Getter;
import lombok.Setter;

// Représente la configuration d'une seule source REST
@Getter
@Setter
public class RestSourceConfig {

    private String url;

    private String apiKey;

    private String method;

    private boolean enabled;

    private int timeout;

    private String description;

    private int retry;
}