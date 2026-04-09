package com.yesmind.agent.ai.market_feedback.adapter.web;

import com.yesmind.agent.ai.market_feedback.domain.model.MarketEvent;
import com.yesmind.agent.ai.market_feedback.domain.model.MarketEventFilter;
import com.yesmind.agent.ai.market_feedback.domain.model.PagedResult;
import com.yesmind.agent.ai.market_feedback.port.core.GetMarketEventsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/market-events")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class MarketEventController {

    private final GetMarketEventsUseCase useCase;
    private final MarketEventMapper mapper;
/*
intercepte l'URL exp :GET /api/market-events?search=bvmt&source=newsapi.org&page=0&size=10,
extrait les paramètres, construit le MarketEventFilter et le passe au service.
 */
    @GetMapping
    public ResponseEntity<PagedResultDTO<MarketEventDTO>> getAll(
            @RequestParam(defaultValue = "")  String search,
            @RequestParam(defaultValue = "") String source,
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        MarketEventFilter filter = MarketEventFilter.builder()
                .search(search)
                .source(source)
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
/*
endpoint séparé du listing
pour ne pas surcharger chaque requête de pagination avec des calculs lourds.
Les stats sont chargées une seule fois au démarrage.
 */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> getStats() {
        return ResponseEntity.ok(Map.of(
                "today",   useCase.countToday(),//ticket de nb d'aujourdhui
                "sources", useCase.countDistinctSources()//ticket de suorces actives
        ));
    }

}