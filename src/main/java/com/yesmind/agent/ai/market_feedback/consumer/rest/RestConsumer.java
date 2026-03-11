package com.yesmind.agent.ai.market_feedback.consumer.rest;

import com.yesmind.agent.ai.market_feedback.consumer.IDataSourceConsumer;
import com.yesmind.agent.ai.market_feedback.domain.model.MarketEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestConsumer implements IDataSourceConsumer {

    private static final Logger log = LoggerFactory.getLogger(RestConsumer.class);

    @Override
    public List<MarketEvent> consume() {
        log.info("Appel REST API...");
        // TODO : logique REST
        return List.of();
    }

    @Override
    public String getSourceName() {
        return "REST_API";
    }
}