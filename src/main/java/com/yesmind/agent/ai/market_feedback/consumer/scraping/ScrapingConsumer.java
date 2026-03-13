package com.yesmind.agent.ai.market_feedback.consumer.scraping;

import com.yesmind.agent.ai.market_feedback.consumer.IDataSourceConsumer;
import com.yesmind.agent.ai.market_feedback.domain.model.MarketEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScrapingConsumer implements IDataSourceConsumer {

    private static final Logger log = LoggerFactory.getLogger(ScrapingConsumer.class);

    @Override
    public List<MarketEvent> consume() {
        log.info("Scraping en cours...");
        // TODO : logique Scraping
        return List.of();
    }

    @Override
    public String getSourceName() {
        return "SCRAPING";
    }
}