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

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RepositoryAdapter implements IRepository, IMarketEventQuery {

    private static final Logger log = LoggerFactory.getLogger(RepositoryAdapter.class);
    private final MarketEventMongoRepository mongoRepository;
    private long generateSequentialId() {
        return mongoRepository.count() + 1;
    }
    @Override
    public void save(MarketEvent event) {
        MarketEventDocument document = MarketEventDocument.builder()
                .id(String.valueOf(generateSequentialId()))
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
        Pageable pageable = PageRequest.of(
                filter.getPage(),
                filter.getSize(),
                Sort.by(Sort.Direction.DESC, "creationDate")
        );

        Page<MarketEventDocument> page;
        String search = filter.getSearch();

        if (search != null && !search.isBlank()) {
            page = mongoRepository.findByContentContainingIgnoreCaseOrSourceUrlContainingIgnoreCase(
                    search, search, pageable
            );
        } else {
            page = mongoRepository.findAll(pageable);
        }

        List<MarketEvent> events = page.getContent()
                .stream()
                .map(this::toMarketEvent)
                .collect(Collectors.toList());

        return PagedResult.<MarketEvent>builder()
                .content(events)
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .currentPage(filter.getPage())
                .build();
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