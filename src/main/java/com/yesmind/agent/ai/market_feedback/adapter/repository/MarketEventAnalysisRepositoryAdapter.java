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

        boolean hasSearch   = filter.getSearch()   != null && !filter.getSearch().isBlank();
        boolean hasUrgence  = filter.getUrgence()  != null && !filter.getUrgence().isBlank();
        boolean hasFamille  = filter.getFamille()  != null && !filter.getFamille().isBlank();

        List<MarketEventAnalysisDocument> docs;

        if (hasSearch && hasUrgence) {
            Set<String> ids = new LinkedHashSet<>();
            List<MarketEventAnalysisDocument> merged = new ArrayList<>();

            mongoRepository
                    .findByThemesUrgenceAndThemesPredictionContainingIgnoreCase(
                            filter.getUrgence(), filter.getSearch())
                    .forEach(d -> { if (ids.add(d.getId())) merged.add(d); });

            mongoRepository
                    .findByThemesUrgenceAndThemesPropositionsContainingIgnoreCase(
                            filter.getUrgence(), filter.getSearch())
                    .forEach(d -> { if (ids.add(d.getId())) merged.add(d); });

            docs = merged;

        } else if (hasUrgence) {
            docs = mongoRepository.findByThemesUrgenceIgnoreCase(filter.getUrgence());

        } else if (hasSearch) {
            Set<String> ids = new LinkedHashSet<>();
            List<MarketEventAnalysisDocument> merged = new ArrayList<>();

            mongoRepository
                    .findByThemesPredictionContainingIgnoreCase(filter.getSearch())
                    .forEach(d -> { if (ids.add(d.getId())) merged.add(d); });

            mongoRepository
                    .findByThemesPropositionsContainingIgnoreCase(filter.getSearch())
                    .forEach(d -> { if (ids.add(d.getId())) merged.add(d); });

            docs = merged;

        } else if (hasFamille) {
            docs = mongoRepository.findByFamilleIgnoreCase(filter.getFamille());



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
                .totalThemes(doc.getTotalThemes())
                .pageDB(doc.getPageDB())
                .totalPagesDB(doc.getTotalPagesDB())
                .analyseLe(doc.getAnalyseLe())
                .themes(
                        doc.getThemes() == null ? List.of() :
                                doc.getThemes().stream()
                                        .map(this::toThemeDomain)
                                        .collect(Collectors.toList())
                )
                .build();
    }

    private MarketEventAnalysis.ThemeAnalysis toThemeDomain(
            MarketEventAnalysisDocument.ThemeAnalysis t) {
        return MarketEventAnalysis.ThemeAnalysis.builder()
                .theme(t.getTheme())
                .prediction(t.getPrediction())
                .propositions(t.getPropositions())   // Map<String, String>
                .ton(t.getTon())
                .urgence(t.getUrgence())
                .categorie(t.getCategorie())
                .analyseLe(t.getAnalyseLe())
                .build();
    }
}