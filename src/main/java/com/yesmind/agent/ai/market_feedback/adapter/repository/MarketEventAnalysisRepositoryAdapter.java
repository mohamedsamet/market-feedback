package com.yesmind.agent.ai.market_feedback.adapter.repository;

import com.yesmind.agent.ai.market_feedback.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MarketEventAnalysisRepositoryAdapter {

    private final MarketEventAnalysisMongoRepository mongoRepository;

    public PagedResult<MarketEventAnalysis> findAll(MarketEventAnalysisFilter filter) {

        boolean hasSearch  = filter.getSearch()      != null && !filter.getSearch().isBlank();
        boolean hasUrgence = filter.getUrgence() != null && !filter.getUrgence().isBlank();

        List<MarketEventAnalysisDocument> docs;

        if (hasSearch && hasUrgence) {
            docs = mongoRepository
                    .findByUrgenceIgnoreCaseAndPredictionContainingIgnoreCaseOrUrgenceIgnoreCaseAndPropositionContainingIgnoreCase(
                            filter.getUrgence(), filter.getSearch(),
                            filter.getUrgence(), filter.getSearch());
        } else if (hasUrgence) {
            docs = mongoRepository.findByUrgenceIgnoreCase(filter.getUrgence());
        } else if (hasSearch) {
            docs = mongoRepository
                    .findByPredictionContainingIgnoreCaseOrPropositionContainingIgnoreCase(
                            filter.getSearch(), filter.getSearch());
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
                .proposition(doc.getProposition())
                .ton(doc.getTon())
                .urgence(doc.getUrgence())
                .categorie(doc.getCategorie())
                .analyseEl(doc.getAnalyseEl())
                .build();
    }
}