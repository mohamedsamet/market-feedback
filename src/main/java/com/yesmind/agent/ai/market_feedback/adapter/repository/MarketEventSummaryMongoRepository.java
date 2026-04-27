package com.yesmind.agent.ai.market_feedback.adapter.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface MarketEventSummaryMongoRepository
        extends MongoRepository<MarketEventSummaryDocument, String> {

    // search dans contenu_fr OU contenu_en OU theme
    Page<MarketEventSummaryDocument>
    findByContenuFrContainingIgnoreCaseOrContenuEnContainingIgnoreCaseOrThemeContainingIgnoreCase(
            String contenuFr, String contenuEn, String theme, Pageable pageable
    );

    // compter le nombre de types distincts (RSS, REST, SCRAPING...)
    @Aggregation(pipeline = {
            "{ $group: { _id: '$type' } }",
            "{ $count: 'total' }"
    })
    long countDistinctTypes();
}