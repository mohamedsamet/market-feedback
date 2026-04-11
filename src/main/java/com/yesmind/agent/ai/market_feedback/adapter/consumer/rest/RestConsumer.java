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
import java.util.concurrent.atomic.AtomicInteger;

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
            StringBuilder url =new StringBuilder(source.getUrl()+ "?")  ;
            AtomicInteger i = new AtomicInteger();
            source.getParams().forEach(param -> {
                url.append(param.getKey());
                url.append("=");
                url.append(param.getValue());
                int count = i.incrementAndGet();
                if(count!=0&&count!=source.getParams().size()){
                    url.append("&");
                }
            });
            Object rawResponse = restTemplate.getForObject(url.toString(), Object.class);

            if(source.getContentPath()!=null){
                Map<String, Object> map = (Map<String, Object>) rawResponse;
                List<Map<String, Object>> items = (List<Map<String, Object>>) map.get(source.getContentPath());
                items.forEach(rawMap -> {
                    MarketEvent event = mapper.convertValue(rawMap, MarketEvent.class);
                    event.setContent(rawMap.toString());
                    event.setSourceUrl(url.toString());
                    event.setId(UUID.randomUUID().toString());
                    event.setCreationDate(LocalDateTime.now());
                    event.setSourceType(SourceType.REST);
                    allEvents.add(event);
                });
            }
            else {
                MarketEvent event = mapper.convertValue(rawResponse, MarketEvent.class);
                event.setContent(rawResponse.toString());
                event.setSourceUrl(url.toString());
                event.setId(UUID.randomUUID().toString());
                event.setCreationDate(LocalDateTime.now());
                event.setSourceType(SourceType.REST);
                allEvents.add(event);
            }
            log.info("Appel REST API : {}", source.getDescription());
          // ou RSS / Scraping
            log.info("MarketEvent créé depuis : {}", source.getDescription());
        });

        return allEvents;
    }

    @Override
    public String getSourceName() {
        return "REST_API";
    }
}