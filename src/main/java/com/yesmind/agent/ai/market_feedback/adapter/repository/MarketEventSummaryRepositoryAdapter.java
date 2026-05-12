package com.yesmind.agent.ai.market_feedback.adapter.repository;

import com.yesmind.agent.ai.market_feedback.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MarketEventSummaryRepositoryAdapter {

    private final MarketEventSummaryMongoRepository mongoRepository;
    private final MongoTemplate mongoTemplate;  // ← ajout

    public PagedResult<MarketEventSummary> findAll(MarketEventSummaryFilter filter) {

        Pageable pageable = PageRequest.of(
                filter.getPage(),
                filter.getSize(),
                Sort.by(Sort.Direction.DESC, "genere_le")
        );

        Page<MarketEventSummaryDocument> page;

        if (StringUtils.hasText(filter.getSearch())) {
            // recherche dans famille, themes[].theme, themes[].contenu_fr/en
            Criteria criteria = new Criteria().orOperator(
                    Criteria.where("famille").regex(filter.getSearch(), "i"),
                    Criteria.where("themes.theme").regex(filter.getSearch(), "i"),
                    Criteria.where("themes.contenu_fr").regex(filter.getSearch(), "i"),
                    Criteria.where("themes.contenu_en").regex(filter.getSearch(), "i")
            );
            Query query = new Query(criteria).with(pageable);
            List<MarketEventSummaryDocument> results =
                    mongoTemplate.find(query, MarketEventSummaryDocument.class);
            long total =
                    mongoTemplate.count(new Query(criteria), MarketEventSummaryDocument.class);
            page = new PageImpl<>(results, pageable, total);
        } else {
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

    public long countDistinctFamilles() {
        return mongoRepository.countDistinctFamilles();
    }

    private MarketEventSummary toDomain(MarketEventSummaryDocument doc) {
        List<MarketEventSummary.ThemeSummary> themes = doc.getThemes() == null
                ? List.of()
                : doc.getThemes().stream()
                .map(t -> MarketEventSummary.ThemeSummary.builder()
                        .theme(t.getTheme())
                        .contenuFr(t.getContenuFr())
                        .contenuEn(t.getContenuEn())
                        .build())
                .collect(Collectors.toList());

        return MarketEventSummary.builder()
                .id(doc.getId())
                .famille(doc.getFamille())
                .genereLe(doc.getGenereLe())
                .pageDB(doc.getPageDB())
                .totalPagesDB(doc.getTotalPagesDB())
                .themes(themes)
                .build();
    }
}