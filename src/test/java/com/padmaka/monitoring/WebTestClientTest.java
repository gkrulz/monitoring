package com.padmaka.monitoring;

import com.padmaka.monitoring.model.Health;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class WebTestClientTest {

    @Autowired
    private WebClient webClient;

    @Test
    void webTestClientTest() {
        Mono<Health> healthMono = webClient.get()
                .uri("/database/health")
                .exchangeToMono(response -> response.bodyToMono(Health.class));

        StepVerifier.create(healthMono)
                .expectNextMatches(health -> health.getStatus().equals("GREEN"))
                .verifyComplete();
    }
}
