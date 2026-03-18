package com.yesmind.agent.ai.market_feedback.port.datasource;

import com.yesmind.agent.ai.market_feedback.domain.model.MarketEvent;
import java.util.List;

public interface DataSourceConsumable {

    List<MarketEvent> consume();

    String getSourceName();
}