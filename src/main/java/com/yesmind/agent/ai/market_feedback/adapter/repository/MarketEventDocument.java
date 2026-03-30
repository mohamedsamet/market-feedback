package com.yesmind.agent.ai.market_feedback.adapter.repository;

import com.yesmind.agent.ai.market_feedback.domain.model.SourceType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

// Représente un MarketEvent stocké dans MongoDB
@Getter
@Setter
@Builder
@Document(collection = "market_events")//Dit à MongoDB de stocker ces objets dans une collection appelée market_events
public class MarketEventDocument {

    @Id
    private String id;
    private String content;
    private String sourceUrl;
    private SourceType sourceType;
    private LocalDateTime creationDate;
}