package com.yesmind.agent.ai.market_feedback.adapter.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;

public interface MarketEventAnalysisMongoRepository
        extends MongoRepository<MarketEventAnalysisDocument, String> {

    // filtre par famille
    List<MarketEventAnalysisDocument> findByFamilleIgnoreCase(String famille);

    // filtre par urgence dans themes[]
    @Query("{ 'themes': { $elemMatch: { 'urgence': { $regex: ?0, $options: 'i' } } } }")
    List<MarketEventAnalysisDocument> findByThemesUrgenceIgnoreCase(String urgence);

    // filtre famille + urgence dans themes[]
    @Query("{ 'famille': { $regex: ?0, $options: 'i' }, " +
            "'themes': { $elemMatch: { 'urgence': { $regex: ?1, $options: 'i' } } } }")
    List<MarketEventAnalysisDocument> findByFamilleAndThemesUrgence(String famille, String urgence);

    // recherche dans themes[].theme
    @Query("{ 'themes': { $elemMatch: { 'theme': { $regex: ?0, $options: 'i' } } } }")
    List<MarketEventAnalysisDocument> findByThemesThemeContainingIgnoreCase(String search);

    // recherche dans themes[].prediction
    @Query("{ 'themes': { $elemMatch: { 'prediction': { $regex: ?0, $options: 'i' } } } }")
    List<MarketEventAnalysisDocument> findByThemesPredictionContainingIgnoreCase(String search);

    // recherche globale : famille OU themes[].theme OU themes[].prediction OU themes[].proposition
    @Query("{ $or: [ " +
            "{ 'famille': { $regex: ?0, $options: 'i' } }, " +
            "{ 'themes': { $elemMatch: { 'theme': { $regex: ?0, $options: 'i' } } } }, " +
            "{ 'themes': { $elemMatch: { 'prediction': { $regex: ?0, $options: 'i' } } } }, " +
            "{ 'themes': { $elemMatch: { 'proposition': { $regex: ?0, $options: 'i' } } } } " +
            "] }")
    List<MarketEventAnalysisDocument> findBySearchIgnoreCase(String search);

    // recherche globale + filtre urgence — sans Pageable
    @Query("{ $and: [ " +
            "{ 'themes': { $elemMatch: { 'urgence': { $regex: ?0, $options: 'i' } } } }, " +
            "{ $or: [ " +
            "{ 'famille': { $regex: ?1, $options: 'i' } }, " +
            "{ 'themes': { $elemMatch: { 'theme': { $regex: ?1, $options: 'i' } } } }, " +
            "{ 'themes': { $elemMatch: { 'prediction': { $regex: ?1, $options: 'i' } } } } " +
            "] } " +
            "] }")
    List<MarketEventAnalysisDocument> findByUrgenceAndSearch(String urgence, String search);
}