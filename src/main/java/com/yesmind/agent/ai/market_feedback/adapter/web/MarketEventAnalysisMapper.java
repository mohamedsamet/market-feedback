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
                .totalThemes(
                        analysis.getThemes() != null
                                ? analysis.getThemes().size()
                                : 0
                )
                .pageDB(analysis.getPageDB())
                .totalPagesDB(analysis.getTotalPagesDB())
                .analyseEl(analysis.getAnalyseEl())
                .themes(toThemeDTOList(analysis.getThemes()))
                .build();
    }

    public List<MarketEventAnalysisDTO> toDTOList(List<MarketEventAnalysis> items) {
        return items.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private MarketEventAnalysisDTO.ThemeDTO toThemeDTO(MarketEventAnalysis.Theme theme) {
        return MarketEventAnalysisDTO.ThemeDTO.builder()
                .theme(theme.getTheme())
                .prediction(theme.getPrediction())
                .proposition(theme.getProposition())
                .ton(theme.getTon())
                .urgence(theme.getUrgence())
                .categorie(theme.getCategorie())
                .analyseEl(theme.getAnalyseEl())
                .build();
    }

    private List<MarketEventAnalysisDTO.ThemeDTO> toThemeDTOList(List<MarketEventAnalysis.Theme> themes) {
        if (themes == null) return List.of();
        return themes.stream()
                .map(this::toThemeDTO)
                .collect(Collectors.toList());
    }
}