package com.yesmind.agent.ai.market_feedback.adapter.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

// Interface qui génère automatiquement toutes les opérations MongoDB
@Repository
public interface MarketEventMongoRepository
        extends MongoRepository<MarketEventDocument, String> {
    // Spring génère automatiquement :
    // save()     → sauvegarder un document
    // findAll()  → récupérer tous les documents
    // findById() → récupérer par id
    // delete()   → supprimer
}