package com.yesmind.agent.ai.market_feedback.adapter.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MarketEventMongoRepository extends MongoRepository<MarketEventDocument, String> {

    Page<MarketEventDocument> findByContentContainingIgnoreCaseOrSourceUrlContainingIgnoreCase(
            String content,
            String sourceUrl,
            Pageable pageable
    );
}