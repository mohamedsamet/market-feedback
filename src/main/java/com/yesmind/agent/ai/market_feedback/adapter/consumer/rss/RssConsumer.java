package com.yesmind.agent.ai.market_feedback.adapter.consumer.rss;

import com.yesmind.agent.ai.market_feedback.port.datasource.DataSourceConsumable;
import com.yesmind.agent.ai.market_feedback.domain.model.MarketEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RssConsumer implements DataSourceConsumable {
    // s'engage à respecter le contrat IDataSourceConsumer. Donc il doit obligatoirement avoir consume() et getSourceName()

    private static final Logger log = LoggerFactory.getLogger(RssConsumer.class);

    @Override
    public List<MarketEvent> consume() {
        log.info("Lecture du flux RSS...");
        // TODO : logique RSS
        return List.of();//retourne liste vide maintenat
    }

    @Override
    public String getSourceName() {
        return "RSS";
    }
}