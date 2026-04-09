package com.yesmind.agent.ai.market_feedback.adapter.web;

import com.yesmind.agent.ai.market_feedback.domain.model.MarketEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

// Convertit MarketEvent (domaine) → MarketEventDTO (frontend)
@Component
public class MarketEventMapper {

    public MarketEventDTO toDTO(MarketEvent event) {
        return MarketEventDTO.builder()
                .id(event.getId())
                .content(event.getContent())
                .sourceUrl(event.getSourceUrl())
                .sourceType(event.getSourceType() != null
                        ? event.getSourceType().name()
                        : null)
                .creationDate(event.getCreationDate())
                .build();
    }

    public List<MarketEventDTO> toDTOList(List<MarketEvent> events) {
        return events.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}