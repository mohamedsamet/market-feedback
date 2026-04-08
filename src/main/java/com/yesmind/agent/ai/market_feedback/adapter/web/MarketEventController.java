package com.yesmind.agent.ai.market_feedback.adapter.web;

import com.yesmind.agent.ai.market_feedback.port.core.GetMarketEventsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// Adapter IN web — expose les MarketEvents via une API REST
@RestController
@RequestMapping("/api/market-events")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173") // autorise le frontend React
public class MarketEventController {

    // le controller ne connaît que le port IN, jamais le service directement
    private final GetMarketEventsUseCase useCase;
    private final MarketEventMapper mapper;

    @GetMapping
    public ResponseEntity<List<MarketEventDTO>> getAll() {
        List<MarketEventDTO> events = mapper.toDTOList(useCase.getAll());
        return ResponseEntity.ok(events);
    }
}