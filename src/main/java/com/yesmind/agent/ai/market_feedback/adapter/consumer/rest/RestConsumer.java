package com.yesmind.agent.ai.market_feedback.adapter.consumer.rest;
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
import java.util.Objects;
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
        config.getRest().forEach(source -> {
            if (!source.isEnabled()) {
                log.info("⏭️ Source désactivée : {}", source.getDescription());
                return;
            }
            String url = source.getUrl() + "&apiKey=" + source.getApiKey()+"from="+source.getFromDate();
            log.info("Appel REST API : {}", source.getDescription());
            MarketEvent event = mapper.convertValue(Objects.requireNonNull(restTemplate.getForObject(url, Object.class)), MarketEvent.class);//transformer directement en market event
            event.setSourceUrl(url);
            event.setId(UUID.randomUUID().toString());
            event.setCreationDate(LocalDateTime.now());
            event.setSourceType(SourceType.REST); // ou RSS / Scraping
            allEvents.add(event);
            log.info("MarketEvent créé depuis : {}", source.getDescription());
        });

        return allEvents;
    }


    @Override
    public String getSourceName() {
        return "REST_API";
    }
}