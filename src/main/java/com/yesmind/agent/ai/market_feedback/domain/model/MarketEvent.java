package com.yesmind.agent.ai.market_feedback.domain.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yesmind.agent.ai.market_feedback.business.annoation.Sanitize;
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
    @Sanitize
    private String content;
    private String sourceUrl;
    private SourceType sourceType;
    private LocalDateTime creationDate;


}