package io.github.vnizar.app.dto;

public record AppErrorResponseDto(
        int status,

        String error,

        String message
) {
}

