package com.yesmind.agent.ai.market_feedback.port.publisher;

import com.yesmind.agent.ai.market_feedback.domain.model.MarketEvent;

public interface IPublisher {
    void publish(MarketEvent event);
}