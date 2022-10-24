package com.padmaka.monitoring.contributor;

import com.padmaka.monitoring.model.Health;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class HealthContributor {

    @Autowired
    private WebClient webClient;

    @Autowired
    private RestTemplate restTemplate;

    private final List<String> healthEndpoints = Arrays.asList(
            "/database/health",
            "/analytic/health",
            "/rule-engine/health",
            "/data-feed/health",
            "/aura/health"
    );

    public Flux<Health> contribute() {
        return Flux.fromIterable(healthEndpoints).flatMap(healthEndpoint -> webClient.get()
                .uri(healthEndpoint)
                .exchangeToMono(response -> {
                    log.info("Retrieving health status for : {}", healthEndpoint);
                    return response.bodyToMono(Health.class);
                })
        );
    }

    public List<Health> contributeWithoutWebFlux() {
        List<Health> healthDependencies = new ArrayList<>();

        healthEndpoints.forEach(healthEndpoint -> {
            log.info("Retrieving health status for : {}", healthEndpoint);
            ResponseEntity<Health> response
                    = restTemplate.exchange(healthEndpoint, HttpMethod.GET, null, Health.class);
            healthDependencies.add(response.getBody());
        });

        return healthDependencies;
    }
}
