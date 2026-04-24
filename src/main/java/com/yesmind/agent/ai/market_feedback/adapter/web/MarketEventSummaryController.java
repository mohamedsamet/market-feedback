package com.yesmind.agent.ai.market_feedback.adapter.web;

import com.yesmind.agent.ai.market_feedback.domain.model.MarketEventSummary;
import com.yesmind.agent.ai.market_feedback.domain.model.MarketEventSummaryFilter;
import com.yesmind.agent.ai.market_feedback.domain.model.PagedResult;
import com.yesmind.agent.ai.market_feedback.port.core.GetMarketEventsSummaryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/market-events-summary")
@RequiredArgsConstructor
public class MarketEventSummaryController extends MainController {

    private final GetMarketEventsSummaryUseCase useCase;
    private final MarketEventSummaryMapper mapper;

    @GetMapping
    public ResponseEntity<PagedResultDTO<MarketEventSummaryDTO>> getAll(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "") String theme,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) {
        MarketEventSummaryFilter filter = MarketEventSummaryFilter.builder()
                .search(search)
                .theme(theme)
                .page(page)
                .size(size)
                .build();

        PagedResult<MarketEventSummary> result = useCase.getAll(filter);

        PagedResultDTO<MarketEventSummaryDTO> response = PagedResultDTO.<MarketEventSummaryDTO>builder()
                .content(mapper.toDTOList(result.getContent()))
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .currentPage(result.getCurrentPage())
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/themes")
    public ResponseEntity<List<String>> getThemes() {
        return ResponseEntity.ok(useCase.getDistinctThemes());
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> getStats() {
        return ResponseEntity.ok(Map.of(
                "today",   useCase.countTotal(),
                "sources", useCase.countDistinctTypes()
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOne(@PathVariable String id) {
        useCase.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMany(@RequestBody List<String> ids) {
        useCase.deleteAllById(ids);
        return ResponseEntity.noContent().build();
    }
}