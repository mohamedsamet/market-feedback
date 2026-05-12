package com.yesmind.agent.ai.market_feedback.adapter.web;

import com.yesmind.agent.ai.market_feedback.domain.model.MarketEventSummary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MarketEventSummaryMapper {

    public MarketEventSummaryDTO toDTO(MarketEventSummary summary) {
        List<MarketEventSummaryDTO.ThemeSummaryDTO> themes = summary.getThemes() == null
                ? List.of()
                : summary.getThemes().stream()
                .map(t -> MarketEventSummaryDTO.ThemeSummaryDTO.builder()
                        .theme(t.getTheme())
                        .contenuFr(t.getContenuFr())
                        .contenuEn(t.getContenuEn())
                        .build())
                .collect(Collectors.toList());

        return MarketEventSummaryDTO.builder()
                .id(summary.getId())
                .famille(summary.getFamille())
                .genereLe(summary.getGenereLe())
                .pageDB(summary.getPageDB())
                .totalPagesDB(summary.getTotalPagesDB())
                .themes(themes)
                .build();
    }

    public List<MarketEventSummaryDTO> toDTOList(List<MarketEventSummary> items) {
        return items.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}