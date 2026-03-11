package com.yesmind.agent.ai.market_feedback.consumer;

import com.yesmind.agent.ai.market_feedback.domain.model.MarketEvent;
import java.util.List;

public interface IDataSourceConsumer {

    List<MarketEvent> consume();

    String getSourceName();
}