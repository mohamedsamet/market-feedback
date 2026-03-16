package com.yesmind.agent.ai.market_feedback.consumer.rest;

import com.yesmind.agent.ai.market_feedback.consumer.IDataSourceConsumer;
import com.yesmind.agent.ai.market_feedback.domain.model.MarketEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
import java.time.LocalDateTime;

import java.util.List;
import java.util.UUID;

@Component
public class RestConsumer implements IDataSourceConsumer {

    private static final Logger log = LoggerFactory.getLogger(RestConsumer.class);
    private final RestTemplate restTemplate = new RestTemplate();

    private static final String API_URL =
            "https://newsapi.org/v2/everything?q=Apple&from=2026-03-05&sortBy=popularity&apiKey=12213de84a224ca5af1d28dde90068be";
    @Override
    public List<MarketEvent> consume() {

        log.info("Appel REST API : {}", API_URL);


        Map<String, Object> response = restTemplate.getForObject(API_URL, Map.class);
        log.info("Réponse : {}", response);

        if (response == null || !response.containsKey("articles")) {
            return List.of();
        }

        List<Map<String, Object>> articles = (List<Map<String, Object>>) response.get("articles");

        return articles.stream()
                .map(article -> new MarketEvent(
                        UUID.randomUUID().toString(),
                        (String) article.get("title"),
                        (String) article.get("description"),
                        (String) article.get("url"),
                        "REST_API",
                        LocalDateTime.now()
                ))
                .toList();

    }

    @Override
    public String getSourceName() {
        return "REST_API";
    }
}