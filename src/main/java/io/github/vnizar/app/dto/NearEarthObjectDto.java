package io.github.vnizar.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record NearEarthObjectDto(
        @JsonProperty("id")
        String id,

        @JsonProperty("name")
        String name,

        @JsonProperty("estimated_diameter")
        EstimatedDiameterDto estimatedDiameter,

        @JsonProperty("is_potentially_hazardous_asteroid")
        boolean isPotentiallyHazard,

        @JsonProperty("close_approach_data")
        List<CloseApproachDataDto> closeApproachData
) {
}
