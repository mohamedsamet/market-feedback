package com.yesmind.agent.ai.market_feedback.consumer;

import com.yesmind.agent.ai.market_feedback.domain.model.MarketEvent;

public interface IRepository {
    void save(MarketEvent event);
}