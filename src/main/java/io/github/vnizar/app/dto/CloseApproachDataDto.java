package io.github.vnizar.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CloseApproachDataDto(
        @JsonProperty("miss_distance")
        MissDistanceDto missDistance
) {
}
