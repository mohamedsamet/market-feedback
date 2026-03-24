package com.yesmind.agent.ai.market_feedback.adapter.repository;

import com.yesmind.agent.ai.market_feedback.domain.model.MarketEvent;
import com.yesmind.agent.ai.market_feedback.port.repository.IRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RepositoryAdapter implements IRepository {

    private static final Logger log = LoggerFactory.getLogger(RepositoryAdapter.class);

    @Override
    public void save(MarketEvent event) {
        // TODO : branchement BDD plus tard
        log.info("Sauvegarde : {}", event.getSourceUrl());
    }
}