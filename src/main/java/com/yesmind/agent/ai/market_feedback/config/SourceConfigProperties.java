package com.yesmind.agent.ai.market_feedback.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "market.feedback")
public class SourceConfigProperties {

    private List<SourceEntry> rest;
    private List<SourceEntry> rss;
    private List<SourceEntry> scraping;

    @Getter
    @Setter
    public static class SourceEntry {
        private String url;
        private boolean enabled;
        private String description;
        private int timeout;
        private int retry;
    }
}