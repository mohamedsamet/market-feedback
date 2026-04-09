package com.yesmind.agent.ai.market_feedback.port.repository;

import com.yesmind.agent.ai.market_feedback.domain.model.MarketEvent;
import com.yesmind.agent.ai.market_feedback.domain.model.MarketEventFilter;
import com.yesmind.agent.ai.market_feedback.domain.model.PagedResult;

import java.util.List;

public interface IRepository {
    void save(MarketEvent event);
    PagedResult<MarketEvent> findAll(MarketEventFilter filter);
    void deleteById(String id);                // ← supprimer un seul
    void deleteAllById(List<String> ids);      // ← supprimer plusieurs
}