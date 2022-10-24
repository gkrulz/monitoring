package com.padmaka.monitoring;

import com.padmaka.monitoring.model.HealthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HealthMonitoringController {

    @Autowired
    private HealthMonitoringService healthMonitoringService;

    //TODO add the time taken in logs
    @GetMapping("/health")
    public Mono<HealthResponse> retrieveHealth() {
        return healthMonitoringService.calculateHealth();
    }

    @GetMapping("/health-without-webflux")
    public HealthResponse retrieveHealthWithoutWebFlux() {
        return healthMonitoringService.calculateHealthWithoutWebFlux();
    }
}
