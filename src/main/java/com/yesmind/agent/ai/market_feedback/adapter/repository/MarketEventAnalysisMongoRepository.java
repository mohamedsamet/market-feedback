package com.yesmind.agent.ai.market_feedback.adapter.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;

public interface MarketEventAnalysisMongoRepository
        extends MongoRepository<MarketEventAnalysisDocument, String> {

    // recherche dans prediction uniquement (propositions est un array, géré séparément)
    List<MarketEventAnalysisDocument> findByPredictionContainingIgnoreCase(String prediction);

    // filtre par urgence
    List<MarketEventAnalysisDocument> findByUrgenceIgnoreCase(String urgence);

    // recherche + filtre urgence
    List<MarketEventAnalysisDocument> findByUrgenceIgnoreCaseAndPredictionContainingIgnoreCase(
            String urgence, String prediction
    );

    // recherche dans propositions (array) via @Query
    @Query("{ 'propositions': { $elemMatch: { $regex: ?0, $options: 'i' } } }")
    List<MarketEventAnalysisDocument> findByPropositionsContainingIgnoreCase(String search);

    // recherche dans propositions + filtre urgence
    @Query("{ 'urgence': { $regex: ?0, $options: 'i' }, 'propositions': { $elemMatch: { $regex: ?1, $options: 'i' } } }")
    List<MarketEventAnalysisDocument> findByUrgenceAndPropositionsContainingIgnoreCase(
            String urgence, String search
    );
}