package com.yesmind.agent.ai.market_feedback.business.consumer;

import com.yesmind.agent.ai.market_feedback.port.core.Consumable;
import com.yesmind.agent.ai.market_feedback.port.repository.IRepository;
import com.yesmind.agent.ai.market_feedback.domain.model.MarketEvent;
import com.yesmind.agent.ai.market_feedback.port.datasource.DataSourceConsumable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Consumer implements Consumable {

    private static final Logger log = LoggerFactory.getLogger(Consumer.class);

    private final List<DataSourceConsumable> sources;
    private final IRepository repository;
    // private final IPublisher publisher;

    @Override
    public void consume() {
        sources.forEach(this::collectFromSource);
    }
    //Pour chaque source dans la liste apelle la methode :collectFromSource
    private void collectFromSource(DataSourceConsumable source) {
        log.info("Collecte depuis : {}", source.getSourceName());
        List<MarketEvent> events = source.consume();
        events.forEach(this::processEvent);
        log.info("{} événements collectés depuis {}", events.size(), source.getSourceName());
    }
    //Stocke les articles récupéré de chaque source et Pour chaque article, appelle processEvent
    @Transactional
    public void processEvent(MarketEvent event) {
        repository.save(event);

    }
}