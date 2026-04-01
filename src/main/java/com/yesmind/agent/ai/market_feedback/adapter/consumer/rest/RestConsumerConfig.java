package com.yesmind.agent.ai.market_feedback.adapter.consumer.rest;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.List;
//Lire les URLs depuis application.properties et les rendre disponibles pour RestConsumer.
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "market.feedback")
public class RestConsumerConfig {

    private List<RestSourceConfig> rest;
}