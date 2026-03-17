package com.yesmind.agent.ai.market_feedback;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@Slf4j
@SpringBootApplication
@EnableScheduling

public class MarketFeedbackApplication {
	public static void main(String[] args) {
		SpringApplication.run(MarketFeedbackApplication.class, args);
	}

}
