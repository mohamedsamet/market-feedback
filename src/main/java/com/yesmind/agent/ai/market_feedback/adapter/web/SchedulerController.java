package com.yesmind.agent.ai.market_feedback.adapter.web;

import com.yesmind.agent.ai.market_feedback.business.core.scheduler.CollectionScheduler;
import com.yesmind.agent.ai.market_feedback.config.SchedulerConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/settings/scheduler")
@RequiredArgsConstructor
public class SchedulerController extends MainController {

    private final SchedulerConfig     schedulerConfig;
    private final CollectionScheduler collectionScheduler;


    @GetMapping
    public ResponseEntity<Map<String, String>> getCron() {
        return ResponseEntity.ok(
                Map.of("cron", schedulerConfig.getCron())
        );
    }


    @PostMapping
    public ResponseEntity<Map<String, String>> updateCron(
            @RequestBody Map<String, String> body) {

        String newCron = body.get("cron");

        if (newCron == null || newCron.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Le champ 'cron' est obligatoire."));
        }

        if (!CronExpression.isValidExpression(newCron)) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Expression cron invalide : " + newCron));
        }

        collectionScheduler.reschedule(newCron);

        return ResponseEntity.ok(Map.of(
                "cron",   newCron,
                "status", "updated"
        ));
    }
}