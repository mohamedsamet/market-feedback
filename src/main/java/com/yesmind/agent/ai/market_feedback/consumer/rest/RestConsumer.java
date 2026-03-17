package com.yesmind.agent.ai.market_feedback.consumer.rest;

import com.yesmind.agent.ai.market_feedback.consumer.IDataSourceConsumer;
import com.yesmind.agent.ai.market_feedback.domain.model.MarketEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class RestConsumer implements IDataSourceConsumer {

    private static final Logger log = LoggerFactory.getLogger(RestConsumer.class);
    private final RestTemplate restTemplate = new RestTemplate();

    private static final String API_URL =
            "https://newsapi.org/v2/everything?q=Apple&from=2026-03-05&sortBy=popularity&apiKey=12213de84a224ca5af1d28dde90068be";

    @Override
    public List<MarketEvent> consume() {

        log.info("Appel REST API : {}", API_URL);

        Object response = restTemplate.getForObject(API_URL, Object.class);
        log.info("Réponse : {}", response);

        if (response == null) {
            return List.of();
        }

        return List.of();
    }

    @Override
    public String getSourceName() {
        return "REST_API";
    }
}