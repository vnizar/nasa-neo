package io.github.vnizar.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NearEarthObjectDto(
        @JsonProperty("id")
        String id,

        @JsonProperty("name")
        String name,

        @JsonProperty("estimated_diameter")
        EstimatedDiameterDto estimatedDiameter,

        @JsonProperty("is_potentially_hazardous_asteroid")
        boolean isPotentiallyHazard
) {
}
