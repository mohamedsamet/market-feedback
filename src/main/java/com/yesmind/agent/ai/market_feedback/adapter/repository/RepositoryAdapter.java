package com.yesmind.agent.ai.market_feedback.adapter.repository;

import com.yesmind.agent.ai.market_feedback.domain.model.MarketEvent;
import com.yesmind.agent.ai.market_feedback.domain.model.SourceType;
import com.yesmind.agent.ai.market_feedback.port.repository.IMarketEventQuery;
import com.yesmind.agent.ai.market_feedback.port.repository.IRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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
    public List<MarketEvent> findAll() {
        // récupère tous les documents MongoDB
        // et les convertit en MarketEvent du domaine
        return mongoRepository.findAll()
                .stream()
                .map(this::toMarketEvent)
                .collect(Collectors.toList());
    }

    // conversion MarketEventDocument → MarketEvent
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