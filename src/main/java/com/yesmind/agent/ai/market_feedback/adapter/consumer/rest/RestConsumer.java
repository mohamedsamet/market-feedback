package com.yesmind.agent.ai.market_feedback.adapter.consumer.rest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yesmind.agent.ai.market_feedback.domain.model.MarketEvent;
import com.yesmind.agent.ai.market_feedback.port.datasource.DataSourceConsumable;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.yesmind.agent.ai.market_feedback.domain.model.SourceType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Component
@RequiredArgsConstructor

public class RestConsumer implements DataSourceConsumable {

    private static final Logger log = LoggerFactory.getLogger(RestConsumer.class);
    private final RestTemplate restTemplate = new RestTemplate();
    private final RestConsumerConfig config;
    private final ObjectMapper mapper;
    @Override
    public List<MarketEvent> consume() {
        List<MarketEvent> allEvents = new ArrayList<>();
        for (String url : config.getUrls()) {
            log.info("Appel REST API : {}", url);

            Object response = restTemplate.getForObject(url, Object.class);
            if (response == null) {
                log.warn("Aucune réponse depuis : {}", url);
                continue;
            }

            log.info("✅ Réponse reçue depuis : {}", url);

            //on a Transformé la réponse en JsonNode
            JsonNode root = mapper.valueToTree(response);
            JsonNode articles = root.get("articles");
            if (articles != null && articles.isArray()) {

                for (JsonNode node : articles) {

                    MarketEvent event = new MarketEvent();


                    event.setTitle(node.path("title").asText());
                    event.setContent(node.path("description").asText());

                    fillMissingFields(event, url);

                    allEvents.add(event);
                }
            }
        }

        return allEvents;
    }

    // Complète les champs manquants pour chaque événement
    private void fillMissingFields(MarketEvent event, String url) {
        if (event.getId() == null) event.setId(UUID.randomUUID().toString());
        if (event.getSourceUrl() == null) event.setSourceUrl(url);
        if (event.getSourceType() == null) event.setSourceType(SourceType.REST);
        if (event.getCreationDate() == null) event.setCreationDate(LocalDateTime.now());
    }
    @Override
    public String getSourceName() {
        return "REST_API";
    }
}