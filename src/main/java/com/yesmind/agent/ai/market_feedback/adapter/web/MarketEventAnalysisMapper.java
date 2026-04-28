package com.yesmind.agent.ai.market_feedback.adapter.web;

import com.yesmind.agent.ai.market_feedback.domain.model.MarketEventAnalysis;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MarketEventAnalysisMapper {

    public MarketEventAnalysisDTO toDTO(MarketEventAnalysis analysis) {
        return MarketEventAnalysisDTO.builder()
                .id(analysis.getId())
                .sourceId(analysis.getSourceId())
                .theme(analysis.getTheme())
                .type(analysis.getType())
                .genereLe(analysis.getGenereLe())
                .prediction(analysis.getPrediction())
                .proposition(analysis.getProposition())
                .ton(analysis.getTon())
                .urgence(analysis.getUrgence())
                .categorie(analysis.getCategorie())
                .analyseEl(analysis.getAnalyseEl())
                .build();
    }

    public List<MarketEventAnalysisDTO> toDTOList(List<MarketEventAnalysis> items) {
        return items.stream().map(this::toDTO).collect(Collectors.toList());
    }
}