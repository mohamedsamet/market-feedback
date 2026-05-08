package com.yesmind.agent.ai.market_feedback.business.core.scheduler;

import com.yesmind.agent.ai.market_feedback.config.SchedulerConfig;
import com.yesmind.agent.ai.market_feedback.port.core.Consumable;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledFuture;

@Component
@RequiredArgsConstructor
public class CollectionScheduler implements SchedulingConfigurer {

    private static final Logger log = LoggerFactory.getLogger(CollectionScheduler.class);

    private final Consumable      consumer;
    private final SchedulerConfig schedulerConfig;
    private final TaskScheduler   taskScheduler;

    private ScheduledFuture<?> currentTask;

    @Override
    public void configureTasks(ScheduledTaskRegistrar registrar) {
        registrar.setTaskScheduler(taskScheduler);
        schedule(schedulerConfig.getCron());
    }

    public synchronized void reschedule(String newCron) {
        log.info("Reprogrammation : [{}] → [{}]", schedulerConfig.getCron(), newCron);
        schedulerConfig.setCron(newCron);
        if (currentTask != null) {
            currentTask.cancel(false);
        }
        schedule(newCron);
        log.info("Scheduler reprogrammé avec succès : {}", newCron);
    }

    private void schedule(String cron) {
        currentTask = taskScheduler.schedule(
                this::triggerCollection,
                new CronTrigger(cron)
        );
    }

    private void triggerCollection() {
        log.info("Démarrage automatique de la collecte (cron: {})...", schedulerConfig.getCron());
        try {
            consumer.consume();
            log.info("Collecte terminée.");
        } catch (Exception e) {
            log.error("Erreur lors de la collecte automatique", e);
        }
    }
}