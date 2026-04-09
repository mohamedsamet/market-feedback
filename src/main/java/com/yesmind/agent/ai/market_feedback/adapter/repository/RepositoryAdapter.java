package com.yesmind.agent.ai.market_feedback.adapter.repository;

import com.yesmind.agent.ai.market_feedback.domain.model.MarketEvent;
import com.yesmind.agent.ai.market_feedback.domain.model.MarketEventFilter;
import com.yesmind.agent.ai.market_feedback.domain.model.PagedResult;
import com.yesmind.agent.ai.market_feedback.port.repository.IMarketEventQuery;
import com.yesmind.agent.ai.market_feedback.port.repository.IRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RepositoryAdapter implements IRepository, IMarketEventQuery {

    private static final Logger log = LoggerFactory.getLogger(RepositoryAdapter.class);
    private final MarketEventMongoRepository mongoRepository;

    @Override
    public void save(MarketEvent event) {
        MarketEventDocument document = MarketEventDocument.builder()
                .id(event.getId())
                .content(event.getContent())
                .sourceUrl(event.getSourceUrl())
                .sourceType(event.getSourceType())
                .creationDate(event.getCreationDate())
                .build();
        mongoRepository.save(document);
        log.info("💾 Sauvegardé dans MongoDB : {}", event.getSourceUrl());
    }

    @Override
    public PagedResult<MarketEvent> findAll(MarketEventFilter filter) {
        //1 construire la pagination
        Pageable pageable = PageRequest.of(
                filter.getPage(),
                filter.getSize(),
                Sort.by(Sort.Direction.DESC, "creationDate")
        );

        String search = filter.getSearch();
        String source = filter.getSource();
        boolean hasSearch = search != null && !search.isBlank();
        boolean hasSource = source != null && !source.isBlank();
        //2choisir la bonne requête MongoDB selon les filtres actifs
        Page<MarketEventDocument> page;

        if (hasSource && hasSearch) {// les deux filtres actifs → cherche dans les deux
            page = mongoRepository.findBySourceUrlContainingAndContentContainingIgnoreCase(
                    source, search, pageable
            );
        } else if (hasSource) {//source seule → filtre par sourceUrl

            page = mongoRepository.findBySourceUrlContaining(source, pageable);
        } else if (hasSearch) {// search seul → cherche dans content
            page = mongoRepository.findByContentContainingIgnoreCase(
                    search, pageable
            );
        } else {// rien → retourne tout
            page = mongoRepository.findAll(pageable);
        }
        //3. convertir les documents MongoDB en objets domaine
        List<MarketEvent> events = page.getContent()
                .stream()
                .map(this::toMarketEvent)
                .collect(Collectors.toList());
        // 4. emballer dans un PagedResult
        return PagedResult.<MarketEvent>builder()
                .content(events)
                .totalElements(page.getTotalElements())//compte pour la ticket nb total
                .totalPages(page.getTotalPages())
                .currentPage(filter.getPage())
                .build();
    }
//cartes stats
    @Override
    //Carte "Aujourd'hui"
    public long countToday() {
        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = start.plusDays(1);//24h de minuit a minuit
        return mongoRepository.countByCreationDateBetween(start, end);
    }
    @Override
    public long countDistinctSources() {
        return mongoRepository.countDistinctSourceUrls();
    }

    private MarketEvent toMarketEvent(MarketEventDocument doc) {
        MarketEvent event = new MarketEvent();
        event.setId(doc.getId());
        event.setContent(doc.getContent());
        event.setSourceUrl(doc.getSourceUrl());
        event.setSourceType(doc.getSourceType());
        event.setCreationDate(doc.getCreationDate());
        return event;
    }
}