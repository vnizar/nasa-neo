package io.github.vnizar.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EstimatedDiameterDto(
        @JsonProperty("kilometers")
        EstimatedDiameterMinMaxDto kilometers,

        @JsonProperty("meters")
        EstimatedDiameterMinMaxDto meters,

        @JsonProperty("miles")
        EstimatedDiameterMinMaxDto miles,

        @JsonProperty("feet")
        EstimatedDiameterMinMaxDto feet
) {
}
