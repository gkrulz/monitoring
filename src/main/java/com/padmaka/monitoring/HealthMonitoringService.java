package com.padmaka.monitoring;

import com.padmaka.monitoring.contributor.HealthContributor;
import com.padmaka.monitoring.model.Health;
import com.padmaka.monitoring.model.HealthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class HealthMonitoringService {

    @Autowired
    private HealthContributor healthContributor;

    public Mono<HealthResponse> calculateHealth() {
        HealthResponse healthResponse = buildHealthResponse();

        return healthContributor.contribute()
                .map(healthResponse::update)
                .then(Mono.just(healthResponse));
    }

    public HealthResponse calculateHealthWithoutWebFlux() {
        HealthResponse healthResponse = buildHealthResponse();

        healthContributor.contributeWithoutWebFlux()
                .forEach(healthResponse::update);

        return healthResponse;
    }

    private HealthResponse buildHealthResponse() {
        return HealthResponse.builder()
                .overallHealth(
                        Health.builder()
                                .name("Overall health")
                                .status("GREEN")
                                .description("Overall health status")
                                .build()
                ).dependencies(new ArrayList<>())
                .build();
    }


}
