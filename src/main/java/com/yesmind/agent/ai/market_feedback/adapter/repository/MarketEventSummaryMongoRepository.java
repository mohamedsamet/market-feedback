package com.yesmind.agent.ai.market_feedback.adapter.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MarketEventSummaryMongoRepository
        extends MongoRepository<MarketEventSummaryDocument, String> {

    @Aggregation(pipeline = {
            "{ $group: { _id: '$famille' } }",
            "{ $count: 'total' }"
    })
    long countDistinctFamilles();
}