package com.yesmind.agent.ai.market_feedback.adapter.web;

import com.yesmind.agent.ai.market_feedback.domain.model.MarketEventSummary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MarketEventSummaryMapper {

    public MarketEventSummaryDTO toDTO(MarketEventSummary summary) {
        return MarketEventSummaryDTO.builder()
                .id(summary.getId())
                .theme(summary.getTheme())
                .nombreArticles(summary.getNombreArticles())
                .genereLe(summary.getGenereLe())
                .type(summary.getType())
                .contenuFr(summary.getContenuFr())
                .contenuEn(summary.getContenuEn())
                .pageDB(summary.getPageDB())
                .totalPagesDB(summary.getTotalPagesDB())
                .build();
    }

    public List<MarketEventSummaryDTO> toDTOList(List<MarketEventSummary> items) {
        return items.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}