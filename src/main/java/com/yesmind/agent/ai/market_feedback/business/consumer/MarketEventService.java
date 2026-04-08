package com.yesmind.agent.ai.market_feedback.business.consumer;

import com.yesmind.agent.ai.market_feedback.domain.model.MarketEvent;
import com.yesmind.agent.ai.market_feedback.port.core.GetMarketEventsUseCase;
import com.yesmind.agent.ai.market_feedback.port.repository.IMarketEventQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

// Use case — implémente le port IN et utilise le port OUT pour récupérer les données
@Service
@RequiredArgsConstructor
public class MarketEventService implements GetMarketEventsUseCase {

    // port OUT — le service ne connaît que l'interface, jamais MongoDB directement
    private final IMarketEventQuery query;

    @Override
    public List<MarketEvent> getAll() {
        return query.findAll();
    }
}