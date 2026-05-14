package com.yesmind.agent.ai.market_feedback.adapter.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;

public interface MarketEventAnalysisMongoRepository
        extends MongoRepository<MarketEventAnalysisDocument, String> {

    // recherche dans themes.prediction
    @Query("{ 'themes': { $elemMatch: { 'prediction': { $regex: ?0, $options: 'i' } } } }")
    List<MarketEventAnalysisDocument> findByThemesPredictionContainingIgnoreCase(String prediction);

    // filtre par themes.urgence
    @Query("{ 'themes': { $elemMatch: { 'urgence': { $regex: ?0, $options: 'i' } } } }")
    List<MarketEventAnalysisDocument> findByThemesUrgenceIgnoreCase(String urgence);

    // recherche themes.prediction + filtre themes.urgence
    @Query("{ 'themes': { $elemMatch: { 'urgence': { $regex: ?0, $options: 'i' }, 'prediction': { $regex: ?1, $options: 'i' } } } }")
    List<MarketEventAnalysisDocument> findByThemesUrgenceAndThemesPredictionContainingIgnoreCase(
            String urgence, String prediction
    );

    // recherche dans themes.propositions (Map {"0":"..","1":"..","2":".."})
    @Query("{ 'themes': { $elemMatch: { $or: [ " +
            "{ 'propositions.0': { $regex: ?0, $options: 'i' } }, " +
            "{ 'propositions.1': { $regex: ?0, $options: 'i' } }, " +
            "{ 'propositions.2': { $regex: ?0, $options: 'i' } } " +
            "] } } }")
    List<MarketEventAnalysisDocument> findByThemesPropositionsContainingIgnoreCase(String search);

    // recherche dans themes.propositions + filtre themes.urgence
    @Query("{ 'themes': { $elemMatch: { 'urgence': { $regex: ?0, $options: 'i' }, $or: [ " +
            "{ 'propositions.0': { $regex: ?1, $options: 'i' } }, " +
            "{ 'propositions.1': { $regex: ?1, $options: 'i' } }, " +
            "{ 'propositions.2': { $regex: ?1, $options: 'i' } } " +
            "] } } }")
    List<MarketEventAnalysisDocument> findByThemesUrgenceAndThemesPropositionsContainingIgnoreCase(
            String urgence, String search
    );

    // filtre par famille
    List<MarketEventAnalysisDocument> findByFamilleIgnoreCase(String famille);

    // filtre par themes.categorie
    @Query("{ 'themes': { $elemMatch: { 'categorie': { $regex: ?0, $options: 'i' } } } }")
    List<MarketEventAnalysisDocument> findByThemesCategorieIgnoreCase(String categorie);

    // filtre par themes.ton
    @Query("{ 'themes': { $elemMatch: { 'ton': { $regex: ?0, $options: 'i' } } } }")
    List<MarketEventAnalysisDocument> findByThemesTonIgnoreCase(String ton);
}
