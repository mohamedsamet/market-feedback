package com.yesmind.agent.ai.market_feedback.adapter.repository;

import com.yesmind.agent.ai.market_feedback.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MarketEventSummaryRepositoryAdapter {

    private final MarketEventSummaryMongoRepository mongoRepository;

    public PagedResult<MarketEventSummary> findAll(MarketEventSummaryFilter filter) {

        Pageable pageable = PageRequest.of(
                filter.getPage(),
                filter.getSize(),
                Sort.by(Sort.Direction.DESC, "genere_le")
        );

        boolean hasSearch = filter.getSearch() != null && !filter.getSearch().isBlank();

        Page<MarketEventSummaryDocument> page;

        if (hasSearch) {
            // cherche dans contenu_fr OU contenu_en OU theme
            page = mongoRepository
                    .findByContenuFrContainingIgnoreCaseOrContenuEnContainingIgnoreCaseOrThemeContainingIgnoreCase(
                            filter.getSearch(),
                            filter.getSearch(),
                            filter.getSearch(),
                            pageable);
        } else {
            // aucun filtre → retourne tout
            page = mongoRepository.findAll(pageable);
        }

        List<MarketEventSummary> items = page.getContent()
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());

        return PagedResult.<MarketEventSummary>builder()
                .content(items)
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .currentPage(filter.getPage())
                .build();
    }

    public void deleteById(String id) {
        mongoRepository.deleteById(id);
    }

    public void deleteAllById(List<String> ids) {
        mongoRepository.deleteAllById(ids);
    }

    public long count() {
        return mongoRepository.count();
    }

    public long countDistinctTypes() {
        return mongoRepository.countDistinctTypes();
    }

    private MarketEventSummary toDomain(MarketEventSummaryDocument doc) {
        return MarketEventSummary.builder()
                .id(doc.getId())
                .theme(doc.getTheme())
                .nombreArticles(doc.getNombreArticles())
                .genereLe(doc.getGenereLe())
                .type(doc.getType())
                .contenuFr(doc.getContenuFr())
                .contenuEn(doc.getContenuEn())
                .pageDB(doc.getPageDB())
                .totalPagesDB(doc.getTotalPagesDB())
                .build();
    }
}