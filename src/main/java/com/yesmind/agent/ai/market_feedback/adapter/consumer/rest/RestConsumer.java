package com.yesmind.agent.ai.market_feedback.adapter.consumer.rest;

import com.yesmind.agent.ai.market_feedback.port.datasource.DataSourceConsumable;
import com.yesmind.agent.ai.market_feedback.domain.model.MarketEvent;
import org.slf4j.Logger;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.ArrayList;
@Component
@RequiredArgsConstructor

public class RestConsumer implements DataSourceConsumable {

    private static final Logger log = LoggerFactory.getLogger(RestConsumer.class);
    private final RestTemplate restTemplate = new RestTemplate();
    private final RestConsumerConfig config;
    @Override
    public List<MarketEvent> consume() {
        List<MarketEvent> allEvents = new ArrayList<>();
        for (String url : config.getUrls()) {
            log.info("Appel REST API : {}", url);
        Object response = restTemplate.getForObject(url, Object.class);
            if (response == null) {
                log.warn(" Aucune réponse depuis : {}", url);
                continue;
            }
            log.info("✅ Réponse reçue depuis : {}", url);
            // TODO : transformer response en List<MarketEvent>
        }

        return allEvents;
    }

    @Override
    public String getSourceName() {
        return "REST_API";
    }
}