package com.yesmind.agent.ai.market_feedback.adapter.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;

public interface MarketEventMongoRepository extends MongoRepository<MarketEventDocument, String> {

    Page<MarketEventDocument> findByContentContainingIgnoreCaseOrSourceUrlContainingIgnoreCase(
            String content, String sourceUrl, Pageable pageable
    );

    Page<MarketEventDocument> findBySourceUrlContaining(
            String source, Pageable pageable
    );

    Page<MarketEventDocument> findBySourceUrlContainingAndContentContainingIgnoreCase(
            String source, String content, Pageable pageable
    );

    @Aggregation(pipeline = {
            "{ $project: { host: { $arrayElemAt: [ { $split: [ { $arrayElemAt: [ { $split: [ '$sourceUrl', '://' ] }, 1 ] }, '/' ] }, 0 ] } } }",
            "{ $group: { _id: '$host' } }",
            "{ $count: 'total' }"
    })
    long countDistinctSourceUrls();

    long countByCreationDateBetween(LocalDateTime start, LocalDateTime end);
}