package com.yesmind.agent.ai.market_feedback.config;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
@Configuration

public class AppConfig {
    @Primary
    @Bean
    public ObjectMapper objectMapper() {

        return new ObjectMapper();
    }
    @Bean
    public XmlMapper xmlMapper() {
        return new XmlMapper();
    }

}
