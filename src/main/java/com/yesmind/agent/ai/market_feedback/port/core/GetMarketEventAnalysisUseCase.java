package com.yesmind.agent.ai.market_feedback.port.core;

import com.yesmind.agent.ai.market_feedback.domain.model.MarketEventAnalysis;
import com.yesmind.agent.ai.market_feedback.domain.model.MarketEventAnalysisFilter;
import com.yesmind.agent.ai.market_feedback.domain.model.PagedResult;

public interface GetMarketEventAnalysisUseCase {
    PagedResult<MarketEventAnalysis> getAll(MarketEventAnalysisFilter filter);
    long countTotal();
}