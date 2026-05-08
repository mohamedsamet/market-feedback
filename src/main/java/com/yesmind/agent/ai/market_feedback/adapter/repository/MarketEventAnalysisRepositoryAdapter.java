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

        List<MarketEventAnalysisDocument> docs;

        if (hasSearch && hasUrgence) {
            // union : prediction OU propositions, filtrés par urgence
            Set<String> ids = new LinkedHashSet<>();
            List<MarketEventAnalysisDocument> merged = new ArrayList<>();

            mongoRepository
                    .findByUrgenceIgnoreCaseAndPredictionContainingIgnoreCase(
                            filter.getUrgence(), filter.getSearch())
                    .forEach(d -> { if (ids.add(d.getId())) merged.add(d); });

            mongoRepository
                    .findByUrgenceAndPropositionsContainingIgnoreCase(
                            filter.getUrgence(), filter.getSearch())
                    .forEach(d -> { if (ids.add(d.getId())) merged.add(d); });

            docs = merged;

        } else if (hasUrgence) {
            docs = mongoRepository.findByUrgenceIgnoreCase(filter.getUrgence());

        } else if (hasSearch) {
            // union : prediction OU propositions
            Set<String> ids = new LinkedHashSet<>();
            List<MarketEventAnalysisDocument> merged = new ArrayList<>();

            mongoRepository
                    .findByPredictionContainingIgnoreCase(filter.getSearch())
                    .forEach(d -> { if (ids.add(d.getId())) merged.add(d); });

            mongoRepository
                    .findByPropositionsContainingIgnoreCase(filter.getSearch())
                    .forEach(d -> { if (ids.add(d.getId())) merged.add(d); });

            docs = merged;

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
                .sourceId(doc.getSourceId())
                .theme(doc.getTheme())
                .type(doc.getType())
                .genereLe(doc.getGenereLe())
                .prediction(doc.getPrediction())
                .propositions(doc.getPropositions())   // ← List<String>
                .ton(doc.getTon())
                .urgence(doc.getUrgence())
                .categorie(doc.getCategorie())
                .analyseEl(doc.getAnalyseEl())
                .build();
    }
}