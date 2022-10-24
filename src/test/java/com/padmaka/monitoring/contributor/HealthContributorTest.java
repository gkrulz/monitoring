package com.padmaka.monitoring.contributor;

import com.padmaka.monitoring.contributor.HealthContributor;
import com.padmaka.monitoring.model.Health;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class HealthContributorTest {

    @Autowired
    private HealthContributor healthContributor;

    private final List<String> expectedHealthDependencies = Arrays.asList("Database", "Analytic", "Rule-engine", "Data-feed", "Aura");

    @Test
    void healthContributorTest() {
        StepVerifier.create(healthContributor.contribute().log())
                .expectNextCount(5)
                .thenConsumeWhile(health -> health.getStatus().equals("GREEN") && expectedHealthDependencies.contains(health.getName()))
                .verifyComplete();
    }

    @Test
    void healthContributorWithoutWebFlux() {
        List<Health> healthDependencies = healthContributor.contributeWithoutWebFlux();

        Assertions.assertThat(healthDependencies.size()).isEqualTo(5);
        Assertions.assertThat(healthDependencies).filteredOn(health -> health.getStatus().equals("GREEN"));
    }
}
