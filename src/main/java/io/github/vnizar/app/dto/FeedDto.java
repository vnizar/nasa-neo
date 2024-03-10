package io.github.vnizar.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FeedDto(
        @JsonProperty("id")
        String id,

        @JsonProperty("name")
        String name,

        @JsonProperty("date")
        String date,

        @JsonProperty("diameter")
        float diameter
) {}
