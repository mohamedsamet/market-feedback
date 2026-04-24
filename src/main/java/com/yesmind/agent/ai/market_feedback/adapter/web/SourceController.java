package com.yesmind.agent.ai.market_feedback.adapter.web;

import com.yesmind.agent.ai.market_feedback.config.SourceConfigProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sources")
@RequiredArgsConstructor
public class SourceController extends MainController {

    private final SourceConfigProperties props;

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getSources() {
        List<Map<String, Object>> sources = new ArrayList<>();

        // sources REST
        if (props.getRest() != null) {
            props.getRest().forEach(s -> sources.add(Map.of(
                    "type",        "REST",
                    "url",         s.getUrl(),
                    "description", s.getDescription() != null ? s.getDescription() : "",
                    "enabled",     s.isEnabled(),
                    "timeout",     s.getTimeout(),
                    "retry",       s.getRetry()
            )));
        }

        // sources RSS
        if (props.getRss() != null) {
            props.getRss().forEach(s -> sources.add(Map.of(
                    "type",        "RSS",
                    "url",         s.getUrl(),
                    "description", s.getDescription() != null ? s.getDescription() : "",
                    "enabled",     s.isEnabled(),
                    "timeout",     s.getTimeout(),
                    "retry",       s.getRetry()
            )));
        }

        // sources SCRAPING
        if (props.getScraping() != null) {
            props.getScraping().forEach(s -> sources.add(Map.of(
                    "type",        "SCRAPING",
                    "url",         s.getUrl(),
                    "description", s.getDescription() != null ? s.getDescription() : "",
                    "enabled",     s.isEnabled(),
                    "timeout",     s.getTimeout(),
                    "retry",       s.getRetry()
            )));
        }

        return ResponseEntity.ok(sources);
    }
}