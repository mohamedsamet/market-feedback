package com.yesmind.agent.ai.market_feedback.business.consumer;

import com.yesmind.agent.ai.market_feedback.domain.model.MarketEvent;
import com.yesmind.agent.ai.market_feedback.domain.model.MarketEventFilter;
import com.yesmind.agent.ai.market_feedback.domain.model.PagedResult;
import com.yesmind.agent.ai.market_feedback.port.core.GetMarketEventsUseCase;
import com.yesmind.agent.ai.market_feedback.port.repository.IMarketEventQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
//logique metier :reçoit le filter du controller, le passe au repository
@Service
@RequiredArgsConstructor
public class MarketEventService implements GetMarketEventsUseCase {

    private final IMarketEventQuery query;

    @Override
    public PagedResult<MarketEvent> getAll(MarketEventFilter filter) {
        return query.findAll(filter);
    }
    @Override
    public long countToday() {
        return query.countToday();
    }
    @Override
    public long countDistinctSources() {
        return query.countDistinctSources();
    }
}