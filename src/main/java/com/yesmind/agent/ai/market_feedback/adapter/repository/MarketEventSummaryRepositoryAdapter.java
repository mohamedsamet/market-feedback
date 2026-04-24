package com.yesmind.agent.ai.market_feedback.adapter.repository;

import com.yesmind.agent.ai.market_feedback.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MarketEventSummaryRepositoryAdapter {

    private final MarketEventSummaryMongoRepository mongoRepository;

    public PagedResult<MarketEventSummary> findAll(MarketEventSummaryFilter filter) {

        // 1. construire la pagination triée par date décroissante
        Pageable pageable = PageRequest.of(
                filter.getPage(),
                filter.getSize(),
                Sort.by(Sort.Direction.DESC, "genere_le")
        );

        boolean hasSearch = filter.getSearch() != null && !filter.getSearch().isBlank();
        boolean hasTheme  = filter.getTheme()  != null && !filter.getTheme().isBlank();

        Page<MarketEventSummaryDocument> page;

        // 2. choisir la bonne requête selon les filtres actifs
        if (hasSearch && hasTheme) {
            // les deux actifs → cherche dans contenu ET filtre par theme
            page = mongoRepository
                    .findByThemeAndContenuFrContainingIgnoreCaseOrThemeAndContenuEnContainingIgnoreCase(
                            filter.getTheme(), filter.getSearch(),
                            filter.getTheme(), filter.getSearch(),
                            pageable);
        } else if (hasTheme) {
            // theme seul → filtre exact par theme
            page = mongoRepository.findByTheme(filter.getTheme(), pageable);
        } else if (hasSearch) {
            // search seul → cherche dans contenu_fr OU contenu_en
            page = mongoRepository
                    .findByContenuFrContainingIgnoreCaseOrContenuEnContainingIgnoreCase(
                            filter.getSearch(), filter.getSearch(), pageable);
        } else {
            // aucun filtre → retourne tout
            page = mongoRepository.findAll(pageable);
        }

        // 3. convertir les documents MongoDB en objets domaine
        List<MarketEventSummary> items = page.getContent()
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());

        // 4. emballer dans un PagedResult
        return PagedResult.<MarketEventSummary>builder()
                .content(items)
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .currentPage(filter.getPage())
                .build();
    }

    // récupérer la liste des thèmes distincts pour la liste déroulante
    public List<String> findDistinctThemes() {
        return mongoRepository.findDistinctThemes();
    }

    // supprimer un seul document
    public void deleteById(String id) {
        mongoRepository.deleteById(id);
    }

    // supprimer plusieurs documents
    public void deleteAllById(List<String> ids) {
        mongoRepository.deleteAllById(ids);
    }

    // compter le total de documents
    public long count() {
        return mongoRepository.count();
    }

    // compter le nombre de types distincts
    public long countDistinctTypes() {
        return mongoRepository.countDistinctTypes();
    }

    // convertir un document MongoDB en objet domaine
    private MarketEventSummary toDomain(MarketEventSummaryDocument doc) {
        return MarketEventSummary.builder()
                .id(doc.getId())
                .theme(doc.getTheme())
                .nombreArticles(doc.getNombreArticles())
                .genereLe(doc.getGenereLe())
                .type(doc.getType())
                .contenuFr(doc.getContenuFr())
                .contenuEn(doc.getContenuEn())
                .pageDB(doc.getPageDB())
                .totalPagesDB(doc.getTotalPagesDB())
                .build();
    }
}