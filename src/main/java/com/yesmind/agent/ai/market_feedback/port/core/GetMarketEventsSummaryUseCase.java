package com.yesmind.agent.ai.market_feedback.port.core;

import com.yesmind.agent.ai.market_feedback.domain.model.MarketEventSummary;
import com.yesmind.agent.ai.market_feedback.domain.model.MarketEventSummaryFilter;
import com.yesmind.agent.ai.market_feedback.domain.model.PagedResult;
import java.util.List;

public interface GetMarketEventsSummaryUseCase {
    PagedResult<MarketEventSummary> getAll(MarketEventSummaryFilter filter);
    long countTotal();
    long countDistinctTypes();
    void deleteById(String id);
    void deleteAllById(List<String> ids);
}