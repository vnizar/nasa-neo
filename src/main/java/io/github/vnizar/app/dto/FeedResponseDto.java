package io.github.vnizar.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public record FeedResponseDto(
        @JsonProperty("near_earth_objects")
        Map<String, List<NearEarthObjectDto>> nearEarthObjects,

        @JsonProperty("element_count")
        int elementCount
) {
}
