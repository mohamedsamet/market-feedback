package com.yesmind.agent.ai.market_feedback.consumer.scheduler;

import com.yesmind.agent.ai.market_feedback.consumer.IConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CollectionScheduler {

    private static final Logger log = LoggerFactory.getLogger(CollectionScheduler.class);

    private final IConsumer consumer;

    public CollectionScheduler(IConsumer consumer) {
        this.consumer = consumer;
    }
    /*Spring démarre
    → voit CollectionScheduler a besoin d'un IConsumer
            → cherche un @Component qui implements IConsumer
    → trouve Consumer
    → l'injecte ✅
*/
    @Scheduled(cron = "0 0 * * * *")
    /*"Déclenche quand seconde = 0 ET minute = 0, peu importe le reste"

Donc toutes les heures pile.*/
    public void trigger() {
        log.info(" Démarrage automatique de la collecte...");
        consumer.consume();
        log.info("Collecte terminée.");
    }
}