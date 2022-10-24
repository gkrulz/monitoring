package com.padmaka.monitoring.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealthResponse {

    private Health overallHealth;
    private List<Health> dependencies = new ArrayList<>();

    public HealthResponse update(Health health) {
        if (health.getStatus().equals("RED")) {
            overallHealth.setStatus("RED");
        }
        dependencies.add(health);
        return this;
    }
}
