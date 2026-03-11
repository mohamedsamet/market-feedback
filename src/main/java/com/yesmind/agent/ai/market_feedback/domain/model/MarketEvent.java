package com.yesmind.agent.ai.market_feedback.domain.model;

import java.time.LocalDateTime;

public class MarketEvent {

    private String id;
    private String title;
    private String content;
    private String sourceUrl;
    private String sourceType;
    private LocalDateTime collectedAt;

    public MarketEvent(String id, String title, String content,
                       String sourceUrl, String sourceType,
                       LocalDateTime collectedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.sourceUrl = sourceUrl;
        this.sourceType = sourceType;
        this.collectedAt = collectedAt;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getSourceUrl() { return sourceUrl; }
    public String getSourceType() { return sourceType; }
    public LocalDateTime getCollectedAt() { return collectedAt; }
}