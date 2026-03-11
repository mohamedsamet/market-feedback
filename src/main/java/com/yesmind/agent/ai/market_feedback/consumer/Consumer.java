package com.yesmind.agent.ai.market_feedback.consumer;

import com.yesmind.agent.ai.market_feedback.domain.model.MarketEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Consumer implements IConsumer {

    private static final Logger log = LoggerFactory.getLogger(Consumer.class);

    private final List<IDataSourceConsumer> sources;
    private final IRepository repository;
    private final IPublisher publisher;

    public Consumer(List<IDataSourceConsumer> sources,
                    IRepository repository,
                    IPublisher publisher) {
        this.sources = sources;
        this.repository = repository;
        this.publisher = publisher;
    }

    @Override
    public void consume() {
        for (IDataSourceConsumer source : sources) {

            log.info("▶ Collecte depuis : {}", source.getSourceName());

            List<MarketEvent> events = source.consume();

            for (MarketEvent event : events) {
                repository.save(event);
                publisher.publish(event);
            }

            log.info("✅ {} événements collectés depuis {}",
                    events.size(), source.getSourceName());
        }
    }
}