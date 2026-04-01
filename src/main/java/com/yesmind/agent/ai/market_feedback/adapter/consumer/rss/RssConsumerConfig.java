package com.yesmind.agent.ai.market_feedback.adapter.consumer.rss;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.List;
// Lire les URLs RSS depuis application.properties et les rendre disponibles pour RssConsumer
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "market.feedback")
public class RssConsumerConfig {

    private List<RssSourceConfig> rss;
}
