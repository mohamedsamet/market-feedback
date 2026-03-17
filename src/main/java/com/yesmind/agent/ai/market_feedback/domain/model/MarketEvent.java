package com.yesmind.agent.ai.market_feedback.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;


import java.time.LocalDateTime;
@Builder

@Getter
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