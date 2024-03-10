package io.github.vnizar.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EstimatedDiameterMinMaxDto(
        @JsonProperty("estimated_diameter_min")
        float estimatedDiameterMin,

        @JsonProperty("estimated_diameter_max")
        float estimatedDiameterMax
) {
}
