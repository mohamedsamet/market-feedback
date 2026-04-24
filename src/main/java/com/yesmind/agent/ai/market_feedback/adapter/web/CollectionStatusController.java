package com.yesmind.agent.ai.market_feedback.adapter.web;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/settings")
@RequiredArgsConstructor
public class CollectionStatusController extends MainController {

    @Value("${scheduler.collection.cron}")
    private String cronExpression;

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getStatus() {
        return ResponseEntity.ok(Map.of(
                "active", true,
                "cron",   cronExpression
        ));
    }
}