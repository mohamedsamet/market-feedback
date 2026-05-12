package com.yesmind.agent.ai.market_feedback.adapter.web;

import com.yesmind.agent.ai.market_feedback.domain.model.MarketEventAnalysis;
import com.yesmind.agent.ai.market_feedback.domain.model.MarketEventAnalysisFilter;
import com.yesmind.agent.ai.market_feedback.port.core.GetMarketEventAnalysisUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carousel")
@RequiredArgsConstructor
public class CarouselController extends MainController {

    private final GetMarketEventAnalysisUseCase useCase;
    private final MarketEventAnalysisMapper mapper;

    @GetMapping
    public ResponseEntity<List<MarketEventAnalysisDTO>> getCarousel(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "") String famille,
            @RequestParam(defaultValue = "") String urgence,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "1000") int size
    ) {
        MarketEventAnalysisFilter filter = MarketEventAnalysisFilter.builder()
                .search(search)
                .famille(famille.isBlank() ? null : famille)
                .urgence(urgence.isBlank() ? null : urgence)
                .page(page)
                .size(size)
                .build();

        List<MarketEventAnalysis> result = useCase.getAll(filter).getContent();
        return ResponseEntity.ok(mapper.toDTOList(result));
    }
}