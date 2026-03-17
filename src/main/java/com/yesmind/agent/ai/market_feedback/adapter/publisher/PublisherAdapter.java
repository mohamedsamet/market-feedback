package com.yesmind.agent.ai.market_feedback.adapter.publisher;

import com.yesmind.agent.ai.market_feedback.domain.model.MarketEvent;
import com.yesmind.agent.ai.market_feedback.port.publisher.IPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PublisherAdapter implements IPublisher {

    private static final Logger log = LoggerFactory.getLogger(PublisherAdapter.class);

    @Override
    public void publish(MarketEvent event) {
        // TODO : branchement Kafka plus tard
        log.info("Publication : {}", event.getTitle());
    }
}
