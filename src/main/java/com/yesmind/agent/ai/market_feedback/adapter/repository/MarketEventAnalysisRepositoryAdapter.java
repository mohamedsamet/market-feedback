package com.yesmind.agent.ai.market_feedback.adapter.repository;

import com.yesmind.agent.ai.market_feedback.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.stream.*;

@Component
@RequiredArgsConstructor
public class MarketEventAnalysisRepositoryAdapter {

    private final MarketEventAnalysisMongoRepository mongoRepository;

    public PagedResult<MarketEventAnalysis> findAll(MarketEventAnalysisFilter filter) {

        boolean hasSearch  = filter.getSearch()  != null && !filter.getSearch().isBlank();
        boolean hasUrgence = filter.getUrgence() != null && !filter.getUrgence().isBlank();
        boolean hasFamille = filter.getFamille() != null && !filter.getFamille().isBlank();

        List<MarketEventAnalysisDocument> docs;

        if (hasSearch && hasUrgence) {
            docs = mongoRepository.findByUrgenceAndSearch(
                    filter.getUrgence(), filter.getSearch());

        } else if (hasFamille && hasUrgence) {
            docs = mongoRepository.findByFamilleAndThemesUrgence(
                    filter.getFamille(), filter.getUrgence());

        } else if (hasFamille) {
            docs = mongoRepository.findByFamilleIgnoreCase(filter.getFamille());

        } else if (hasUrgence) {
            docs = mongoRepository.findByThemesUrgenceIgnoreCase(filter.getUrgence());

        } else if (hasSearch) {
            docs = mongoRepository.findBySearchIgnoreCase(filter.getSearch());

        } else {
            docs = mongoRepository.findAll();
        }

        List<MarketEventAnalysis> items = docs.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());

        return PagedResult.<MarketEventAnalysis>builder()
                .content(items)
                .totalElements(items.size())
                .totalPages(1)
                .currentPage(0)
                .build();
    }

    public long count() {
        return mongoRepository.count();
    }

    private MarketEventAnalysis toDomain(MarketEventAnalysisDocument doc) {
        return MarketEventAnalysis.builder()
                .id(doc.getId())
                .famille(doc.getFamille())
                .genereLe(doc.getGenereLe())
                .totalThemes(doc.getThemes() != null ? doc.getThemes().size() : 0)
                .pageDB(doc.getPageDB())
                .totalPagesDB(doc.getTotalPagesDB())
                .analyseEl(doc.getAnalyseEl())
                .themes(toThemeDomainList(doc.getThemes()))
                .build();
    }

    private List<MarketEventAnalysis.Theme> toThemeDomainList(
            List<MarketEventAnalysisDocument.ThemeDocument> themes) {
        if (themes == null) return List.of();
        return themes.stream()
                .map(t -> MarketEventAnalysis.Theme.builder()
                        .theme(t.getTheme())
                        .prediction(t.getPrediction())
                        .proposition(t.getProposition())
                        .ton(t.getTon())
                        .urgence(t.getUrgence())
                        .categorie(t.getCategorie())
                        .analyseEl(t.getAnalyseEl())
                        .build())
                .collect(Collectors.toList());
    }
}