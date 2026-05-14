package com.yesmind.agent.ai.market_feedback.port.core;

import com.yesmind.agent.ai.market_feedback.adapter.repository.MarketEventAnalysisRepositoryAdapter;
import com.yesmind.agent.ai.market_feedback.domain.model.MarketEventAnalysis;
import com.yesmind.agent.ai.market_feedback.domain.model.MarketEventAnalysisFilter;
import com.yesmind.agent.ai.market_feedback.domain.model.PagedResult;
import com.yesmind.agent.ai.market_feedback.port.core.GetMarketEventAnalysisUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetMarketEventAnalysisUseCaseImpl implements GetMarketEventAnalysisUseCase {

    private final MarketEventAnalysisRepositoryAdapter repositoryAdapter;

    @Override
    public PagedResult<MarketEventAnalysis> getAll(MarketEventAnalysisFilter filter) {
        return repositoryAdapter.findAll(filter);
    }

    @Override
    public long countTotal() {
        return repositoryAdapter.count();
    }
}