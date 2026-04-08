package com.yesmind.agent.ai.market_feedback.port.core;

import com.yesmind.agent.ai.market_feedback.domain.model.MarketEvent;
import java.util.List;

// Port IN — contrat que le Controller appellera pour récupérer les événements
public interface GetMarketEventsUseCase {
    List<MarketEvent> getAll();
}