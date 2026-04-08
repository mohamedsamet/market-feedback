package com.yesmind.agent.ai.market_feedback.adapter.consumer.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yesmind.agent.ai.market_feedback.domain.model.MarketEvent;
import com.yesmind.agent.ai.market_feedback.domain.model.SourceType;
import com.yesmind.agent.ai.market_feedback.port.datasource.DataSourceConsumable;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

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

        config.getRest().forEach(source -> {

            if (!source.isEnabled()) {
                log.info("⏭️ Source désactivée : {}", source.getDescription());
                return;
            }

            try {
                String url = source.getUrl() + "&apiKey=" + source.getApiKey();
                log.info("Appel REST API : {}", source.getDescription());

                // 1️⃣ Appel API
                Object response = restTemplate.getForObject(url, Object.class);

                Map<String, Object> map = mapper.convertValue(response, Map.class);
                List<Map<String, Object>> articles =
                        mapper.convertValue(
                                map.get("articles"),
                                new com.fasterxml.jackson.core.type.TypeReference<List<Map<String, Object>>>() {}
                        );

                for (Map<String, Object> item : articles) {

                    MarketEvent event = new MarketEvent();

                    event.setContent(
                            (String) item.getOrDefault(
                                    "content",
                                    item.getOrDefault(
                                            "description",
                                            item.getOrDefault("title", "")
                                    )
                            )
                    );

                    event.setSourceUrl(url);
                    event.setId(UUID.randomUUID().toString());
                    event.setCreationDate(LocalDateTime.now());
                    event.setSourceType(SourceType.REST);

                    allEvents.add(event);
                }

                log.info("✔ MarketEvent créé depuis : {}", source.getDescription());

            } catch (Exception e) {
                log.error("❌ Erreur API {} : {}", source.getDescription(), e.getMessage());
            }
        });

        return allEvents;
    }

    @Override
    public String getSourceName() {
        return "REST_API";
    }
}