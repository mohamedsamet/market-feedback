package com.yesmind.agent.ai.market_feedback.adapter.repository;

import com.yesmind.agent.ai.market_feedback.domain.model.MarketEvent;
import com.yesmind.agent.ai.market_feedback.port.repository.IRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RepositoryAdapter implements IRepository {
    private final MarketEventMongoRepository mongoRepository;

    private static final Logger log = LoggerFactory.getLogger(RepositoryAdapter.class);

    @Override
    public void save(MarketEvent event) {
        // étape 1 — convertir MarketEvent → MarketEventDocument
        MarketEventDocument document = MarketEventDocument.builder()
                .id(event.getId())
                .content(event.getContent())
                .sourceUrl(event.getSourceUrl())
                .sourceType(event.getSourceType())
                .creationDate(event.getCreationDate())
                .build();

        // étape 2 — sauvegarder dans MongoDB
        mongoRepository.save(document);

        log.info("Sauvegardé dans MongoDB : {}", event.getSourceUrl());
    }
}