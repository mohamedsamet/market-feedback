package com.yesmind.agent.ai.market_feedback.adapter.consumer.scraping;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "market.feedback.scraping")
public class ScrapingConsumerConfig {

    private List<String> urls;
}