package io.github.vnizar.app.service;

import io.github.vnizar.app.common.ValidationException;
import io.github.vnizar.app.dto.FeedDto;
import io.github.vnizar.app.dto.FeedResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NeoServiceImpl implements NeoService {

    @Autowired
    private ExternalNeoService externalNeoService;

    @Override
    public List<FeedDto> getFeed(String startDate, String endDate) {
        if(startDate == null || endDate == null) {
            throw new ValidationException("start date and end date should exist");
        }

        List<FeedDto> result = new ArrayList<>();
        FeedResponseDto response = externalNeoService.getFeed(startDate, endDate);
        if (response != null) {
            response.nearEarthObjects().forEach((date, item) -> {
                item.forEach((itemKey) -> {
                    result.add(new FeedDto(
                            itemKey.id(),
                            itemKey.name(),
                            date,
                            itemKey.estimatedDiameter().kilometers().estimatedDiameterMax()
                    ));
                });
            });
        }

        return result;
    }
}
