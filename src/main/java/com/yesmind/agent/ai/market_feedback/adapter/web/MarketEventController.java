package com.yesmind.agent.ai.market_feedback.adapter.web;

import com.yesmind.agent.ai.market_feedback.domain.model.MarketEvent;
import com.yesmind.agent.ai.market_feedback.domain.model.MarketEventFilter;
import com.yesmind.agent.ai.market_feedback.domain.model.PagedResult;
import com.yesmind.agent.ai.market_feedback.port.core.GetMarketEventsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/market-events")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class MarketEventController {

    private final GetMarketEventsUseCase useCase;
    private final MarketEventMapper mapper;

    @GetMapping
    public ResponseEntity<PagedResultDTO<MarketEventDTO>> getAll(
            @RequestParam(defaultValue = "")  String search,
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        MarketEventFilter filter = MarketEventFilter.builder()
                .search(search)
                .page(page)
                .size(size)
                .build();

        PagedResult<MarketEvent> result = useCase.getAll(filter);

        PagedResultDTO<MarketEventDTO> response = PagedResultDTO.<MarketEventDTO>builder()
                .content(mapper.toDTOList(result.getContent()))
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .currentPage(result.getCurrentPage())
                .build();

        return ResponseEntity.ok(response);
    }
}