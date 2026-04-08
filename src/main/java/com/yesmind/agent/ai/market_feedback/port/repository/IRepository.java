package com.yesmind.agent.ai.market_feedback.port.repository;

import com.yesmind.agent.ai.market_feedback.domain.model.MarketEvent;
import com.yesmind.agent.ai.market_feedback.domain.model.MarketEventFilter;
import com.yesmind.agent.ai.market_feedback.domain.model.PagedResult;

public interface IRepository {
    void save(MarketEvent event);
    PagedResult<MarketEvent> findAll(MarketEventFilter filter);
}