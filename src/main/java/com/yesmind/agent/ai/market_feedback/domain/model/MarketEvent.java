package com.yesmind.agent.ai.market_feedback.domain.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.Setter;


import java.time.LocalDateTime;
@Builder
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class MarketEvent {

    private String id;
    private String title;
    private String content;
    private String sourceUrl;
    private SourceType sourceType;
    private LocalDateTime creationDate;


}