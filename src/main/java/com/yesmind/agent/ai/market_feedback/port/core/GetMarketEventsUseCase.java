package com.yesmind.agent.ai.market_feedback.port.core;

import com.yesmind.agent.ai.market_feedback.domain.model.MarketEventFilter;
import com.yesmind.agent.ai.market_feedback.domain.model.PagedResult;
import com.yesmind.agent.ai.market_feedback.domain.model.MarketEvent;
/*
port in
contrat entre le controller et le service. Le controller ne connaît que cette interface jamais le service directement.

 */
public interface GetMarketEventsUseCase {
    PagedResult<MarketEvent> getAll(MarketEventFilter filter);
    long countToday();
    long countDistinctSources();

}