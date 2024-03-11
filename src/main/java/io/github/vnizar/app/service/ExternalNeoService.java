package io.github.vnizar.app.service;

import io.github.vnizar.app.dto.FeedResponseDto;
import io.github.vnizar.app.dto.NearEarthObjectDto;

import java.util.List;

public interface ExternalNeoService {
    FeedResponseDto getFeed(String startDate, String endDate);

    NearEarthObjectDto getLookup(String id);
}
