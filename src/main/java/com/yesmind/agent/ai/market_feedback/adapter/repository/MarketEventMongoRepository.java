package com.yesmind.agent.ai.market_feedback.adapter.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;

public interface MarketEventMongoRepository extends MongoRepository<MarketEventDocument, String> {
//Cas 1 — search seul: chercher  dans le contenu
    Page<MarketEventDocument> findByContentContainingIgnoreCase(
            String content, Pageable pageable
    );
//Cas 2 — source seule :filtre par source
    Page<MarketEventDocument> findBySourceUrlContaining(
            String source, Pageable pageable
    );
//Cas 3 — source + search ensemble (filtre par source+ recherche dans le contenu)
    Page<MarketEventDocument> findBySourceUrlContainingAndContentContainingIgnoreCase(
            String source, String content, Pageable pageable
    );
//carte source actives
    @Aggregation(pipeline = {
            // étape 1 : extraire le hostname depuis l'URL complète
            "{ $project: { host: { $arrayElemAt: [ { $split: [ { $arrayElemAt: [ { $split: [ '$sourceUrl', '://' ] }, 1 ] }, '/' ] }, 0 ] } } }",
            // étape 2 : grouper par hostname
            "{ $group: { _id: '$host' } }",
            // étape 3 : compter les groupes
            "{ $count: 'total' }"
    })
    long countDistinctSourceUrls();

    long countByCreationDateBetween(LocalDateTime start, LocalDateTime end);
}