package io.github.vnizar.app.service;

import io.github.vnizar.app.dto.FeedResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ExternalNeoServiceImpl implements ExternalNeoService {

    private final String BASE_URL = "https://api.nasa.gov";
    private final String NEO_PATH_VERSION = "/neo/rest/v1";
    @Autowired
    private NetworkService networkService;

    @Override
    public FeedResponseDto getFeed(String startDate, String endDate) {
        ResponseEntity<FeedResponseDto> response = networkService.get(
                BASE_URL + NEO_PATH_VERSION + "/feed?start_date" + startDate + "&end_date" + endDate,
                FeedResponseDto.class
        );
        return response.getBody();
    }
}
