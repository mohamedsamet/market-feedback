package com.yesmind.agent.ai.market_feedback.business.core;

import com.yesmind.agent.ai.market_feedback.adapter.repository.MarketEventSummaryRepositoryAdapter;
import com.yesmind.agent.ai.market_feedback.domain.model.MarketEventSummary;
import com.yesmind.agent.ai.market_feedback.domain.model.MarketEventSummaryFilter;
import com.yesmind.agent.ai.market_feedback.domain.model.PagedResult;
import com.yesmind.agent.ai.market_feedback.port.core.GetMarketEventsSummaryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetMarketEventsSummaryUseCaseImpl implements GetMarketEventsSummaryUseCase {

    private final MarketEventSummaryRepositoryAdapter repositoryAdapter;

    @Override
    public PagedResult<MarketEventSummary> getAll(MarketEventSummaryFilter filter) {
        return repositoryAdapter.findAll(filter);
    }

    @Override
    public long countTotal() {
        return repositoryAdapter.count();
    }

    @Override
    public long countDistinctTypes() {
        return repositoryAdapter.countDistinctTypes();
    }

    @Override
    public List<String> getDistinctThemes() {
        return repositoryAdapter.findDistinctThemes();
    }

    @Override
    public void deleteById(String id) {
        repositoryAdapter.deleteById(id);
    }

    @Override
    public void deleteAllById(List<String> ids) {
        repositoryAdapter.deleteAllById(ids);
    }
}