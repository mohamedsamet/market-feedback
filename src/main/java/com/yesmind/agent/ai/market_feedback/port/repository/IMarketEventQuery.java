package com.yesmind.agent.ai.market_feedback.port.repository;

import com.yesmind.agent.ai.market_feedback.domain.model.MarketEvent;
import com.yesmind.agent.ai.market_feedback.domain.model.MarketEventFilter;
import com.yesmind.agent.ai.market_feedback.domain.model.PagedResult;
/*
port out
contrat entre le service et le repository.
Le service ne connaît que cette interface, jamais MongoDB directement.
 */
public interface IMarketEventQuery {
    PagedResult<MarketEvent> findAll(MarketEventFilter filter);
    long countToday();
    long countDistinctSources();
}