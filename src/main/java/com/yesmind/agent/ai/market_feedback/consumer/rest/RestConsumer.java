package com.yesmind.agent.ai.market_feedback.consumer.rest;

import com.yesmind.agent.ai.market_feedback.consumer.IDataSourceConsumer;
import com.yesmind.agent.ai.market_feedback.domain.model.MarketEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import com.yesmind.agent.ai.market_feedback.consumer.rest.news.NewsResponse;


import java.util.List;
/*
 Appelle l'API NewsAPI et récupère les articles .Au lieu de recevoir un Map, on reçoit directement un objet NewsResponse
  qui contient une liste d'Article, chaque article est ensuite converti en MarketEvent.
 */
@Component
public class RestConsumer implements IDataSourceConsumer {

    private static final Logger log = LoggerFactory.getLogger(RestConsumer.class);
    private final RestTemplate restTemplate = new RestTemplate();

    private static final String API_URL =
            "https://newsapi.org/v2/everything?q=Apple&from=2026-03-05&sortBy=popularity&apiKey=12213de84a224ca5af1d28dde90068be";
    @Override
    public List<MarketEvent> consume() {

        log.info("Appel REST API : {}", API_URL);


        NewsResponse response = restTemplate.getForObject(API_URL, NewsResponse.class);
        log.info("Réponse : {}", response);

        if (response == null) {
            return List.of();
        }


        return response.getArticles().stream()
                .map(article -> MarketEvent.builder()
                        .title(article.getTitle())
                        .content(article.getDescription())
                        .sourceUrl(article.getUrl())
                        .sourceType("REST_API")
                        .collectedAt(LocalDateTime.now())
                        .creationDate(LocalDateTime.now())
                        .build()
                )
                .toList();

    }

    @Override
    public String getSourceName() {
        return "REST_API";
    }
}