package com.yesmind.agent.ai.market_feedback.adapter.consumer.scraping;

import lombok.Getter;
import lombok.Setter;

// Représente la configuration d'une seule source Scraping
@Getter
@Setter
public class ScrapingSourceConfig {

    private String url;

    private boolean enabled;

    private int timeout;

    private String description;

    private int retry;

    private String tags;

    private String userAgent;

    private int maxDepth;
}