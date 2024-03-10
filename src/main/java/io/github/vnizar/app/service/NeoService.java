package io.github.vnizar.app.service;

import io.github.vnizar.app.dto.FeedDto;

import java.util.List;

public interface NeoService {
    List<FeedDto> getFeed(String startDate, String endDate);
}
