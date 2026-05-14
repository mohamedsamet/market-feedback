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
                .famille(analysis.getFamille())
                .genereLe(analysis.getGenereLe())
                .totalThemes(analysis.getTotalThemes())
                .pageDB(analysis.getPageDB())
                .totalPagesDB(analysis.getTotalPagesDB())
                .analyseLe(analysis.getAnalyseLe())
                .themes(
                        analysis.getThemes() == null ? List.of() :
                                analysis.getThemes().stream()
                                        .map(this::toThemeDTO)
                                        .collect(Collectors.toList())
                )
                .build();
    }

    private MarketEventAnalysisDTO.ThemeAnalysisDTO toThemeDTO(
            MarketEventAnalysis.ThemeAnalysis theme) {
        return MarketEventAnalysisDTO.ThemeAnalysisDTO.builder()
                .theme(theme.getTheme())
                .prediction(theme.getPrediction())
                .propositions(theme.getPropositions())  // Map<String, String>
                .ton(theme.getTon())
                .urgence(theme.getUrgence())
                .categorie(theme.getCategorie())
                .analyseLe(theme.getAnalyseLe())
                .build();
    }

    public List<MarketEventAnalysisDTO> toDTOList(List<MarketEventAnalysis> items) {
        return items.stream().map(this::toDTO).collect(Collectors.toList());
    }
}