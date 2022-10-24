package com.padmaka.monitoring;

import com.padmaka.monitoring.model.Health;
import com.padmaka.monitoring.model.HealthResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class WebTestClientTest {

    @Autowired
    private WebClient webClient;

    @Test
    void monitoringWithWebFluxTest() {
        long startTime = System.currentTimeMillis();
        Mono<HealthResponse> healthMono = webClient.get()
                .uri("/health")
                .exchangeToMono(response -> response.bodyToMono(HealthResponse.class));

        StepVerifier.create(healthMono)
                .expectNextMatches((health) -> {
                    log.info("[With WebFlux] Time Spent: {}s", (System.currentTimeMillis() - startTime)/1000);
                    return health.getOverallHealth().getStatus().equals("GREEN");
                })
                .verifyComplete();
    }

    @Test
    void monitoringWithoutWebFluxTest() {
        long startTime = System.currentTimeMillis();
        Mono<HealthResponse> healthMono = webClient.get()
                .uri("/health-without-webflux")
                .exchangeToMono(response -> response.bodyToMono(HealthResponse.class));

        StepVerifier.create(healthMono)
                .expectNextMatches((health) -> {
                    log.info("[Without WebFlux] Time Spent: {}s", (System.currentTimeMillis() - startTime)/1000);
                    return health.getOverallHealth().getStatus().equals("GREEN");
                })
                .verifyComplete();
    }
}
