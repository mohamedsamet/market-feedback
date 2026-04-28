package com.yesmind.agent.ai.market_feedback.adapter.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface MarketEventAnalysisMongoRepository
        extends MongoRepository<MarketEventAnalysisDocument, String> {

    // recherche dans prediction OU proposition
    List<MarketEventAnalysisDocument> findByPredictionContainingIgnoreCaseOrPropositionContainingIgnoreCase(
            String prediction, String proposition
    );

    // filtre par urgence
    List<MarketEventAnalysisDocument> findByUrgenceIgnoreCase(String urgence);

    // recherche + filtre urgence
    List<MarketEventAnalysisDocument> findByUrgenceIgnoreCaseAndPredictionContainingIgnoreCaseOrUrgenceIgnoreCaseAndPropositionContainingIgnoreCase(
            String urgence1, String prediction,
            String urgence2, String proposition
    );
}