package com.yesmind.agent.ai.market_feedback.port.repository;

import com.yesmind.agent.ai.market_feedback.domain.model.MarketEvent;
import java.util.List;

// Port OUT — contrat pour récupérer les MarketEvents depuis la BDD
public interface IMarketEventQuery {
    List<MarketEvent> findAll();
}