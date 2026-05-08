package com.yesmind.agent.ai.market_feedback.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "scheduler.collection")
public class SchedulerConfig {

    private String cron;

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(2);
        scheduler.setThreadNamePrefix("collection-scheduler-");
        scheduler.initialize();
        return scheduler;
    }
}