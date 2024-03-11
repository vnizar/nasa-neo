package io.github.vnizar.app.service;

import io.github.vnizar.app.dto.FeedDto;
import io.github.vnizar.app.dto.NearEarthObjectDto;

import java.util.List;

public interface NeoService {
    List<FeedDto> getFeed(String startDate, String endDate);

    NearEarthObjectDto getLookup(String id);
}
