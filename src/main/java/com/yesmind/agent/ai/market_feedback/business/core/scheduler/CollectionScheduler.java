package com.yesmind.agent.ai.market_feedback.business.core.scheduler;

import com.yesmind.agent.ai.market_feedback.port.core.Consumable;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CollectionScheduler {

    private static final Logger log = LoggerFactory.getLogger(CollectionScheduler.class);

    private final Consumable consumer;

    @Scheduled(cron = "${scheduler.collection.cron}")
    public void trigger() {
        log.info("Démarrage automatique de la collecte...");
        consumer.consume();
        log.info("Collecte terminée.");
    }
}