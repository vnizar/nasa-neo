package io.github.vnizar.app.repository;

import io.github.vnizar.app.dto.FeedResponseDto;

import java.util.List;

public interface NeoRepository {
    List<FeedResponseDto> getFeed();
}
