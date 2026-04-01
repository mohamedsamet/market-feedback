package com.yesmind.agent.ai.market_feedback.adapter.consumer.rss;

import lombok.Getter;
import lombok.Setter;

// Représente la configuration d'une seule source RSS
@Getter
@Setter
public class RssSourceConfig {

    private String url;

    private boolean enabled;

    private int timeout;

    private String description;

    private int retry;

    private int maxItems;

    private String encoding;
}