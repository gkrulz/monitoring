package com.padmaka.monitoring.endpoints;

import com.padmaka.monitoring.model.Health;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/database/health")
    public Health getDatabaseHealth() throws InterruptedException {
        Thread.sleep(3000);
        return Health.builder()
                .name("Database")
                .status("GREEN")
                .description("Database health")
                .build();
    }

    @GetMapping("/analytic/health")
    public Health getAnalyticHealth() throws InterruptedException {
        Thread.sleep(2000);
        return Health.builder()
                .name("Analytic")
                .status("GREEN")
                .description("Analytic health")
                .build();
    }

    @GetMapping("/rule-engine/health")
    public Health getRuleEngineHealth() throws InterruptedException {
        Thread.sleep(1000);
        return Health.builder()
                .name("Rule-engine")
                .status("GREEN")
                .description("Rule-engine health")
                .build();
    }

    @GetMapping("/data-feed/health")
    public Health getDataFeedHealth() throws InterruptedException {
        Thread.sleep(4000);
        return Health.builder()
                .name("Data-feed")
                .status("GREEN")
                .description("Data-feed health")
                .build();
    }

    @GetMapping("/aura/health")
    public Health getAuraHealth() throws InterruptedException {
        Thread.sleep(3000);
        return Health.builder()
                .name("Aura")
                .status("GREEN")
                .description("Aura health")
                .build();
    }
}
