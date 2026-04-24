package com.yesmind.agent.ai.market_feedback.adapter.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface MarketEventSummaryMongoRepository
        extends MongoRepository<MarketEventSummaryDocument, String> {

    // filtre par theme exact
    Page<MarketEventSummaryDocument> findByTheme(
            String theme, Pageable pageable
    );

    // search dans contenu_fr OU contenu_en (sans filtre theme)
    Page<MarketEventSummaryDocument> findByContenuFrContainingIgnoreCaseOrContenuEnContainingIgnoreCase(
            String contenuFr, String contenuEn, Pageable pageable
    );

    // search dans contenu_fr OU contenu_en ET filtre par theme
    // Spring Data duplique le theme car OR nécessite 4 paramètres
    Page<MarketEventSummaryDocument> findByThemeAndContenuFrContainingIgnoreCaseOrThemeAndContenuEnContainingIgnoreCase(
            String theme1, String contenuFr,
            String theme2, String contenuEn,
            Pageable pageable
    );

    // récupérer tous les thèmes distincts triés alphabétiquement
    @Aggregation(pipeline = {
            "{ $group: { _id: '$theme' } }",
            "{ $sort: { _id: 1 } }"
    })
    List<String> findDistinctThemes();

    // compter le nombre de types distincts (RSS, REST, SCRAPING...)
    @Aggregation(pipeline = {
            "{ $group: { _id: '$type' } }",
            "{ $count: 'total' }"
    })
    long countDistinctTypes();
}