package com.yesmind.agent.ai.market_feedback.config;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class appconfig {
    @Bean
    public ObjectMapper objectMapper() {

        return new ObjectMapper();
    }

}
