package io.github.vnizar.app.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.vnizar.app.common.RedisConstant;
import io.github.vnizar.app.common.ValidationException;
import io.github.vnizar.app.dto.FeedDto;
import io.github.vnizar.app.dto.FeedResponseDto;
import io.github.vnizar.app.dto.NearEarthObjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class NeoServiceImpl implements NeoService {

    @Value("${cache.timeout}")
    private int timeout;

    @Autowired
    private ExternalNeoService externalNeoService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<FeedDto> getFeed(String startDate, String endDate, int size) {
        if (startDate == null || endDate == null) {
            throw new ValidationException("start date and end date should exist");
        }

        if (size < 0) {
            size = 1;
        }

        String data = redisTemplate.opsForValue().get(
                RedisConstant.REDIS_FEED_KEY + startDate + "_" + endDate + "_" + size
        );

        if (data != null && !data.isEmpty()) {
            try {
                return objectMapper.readValue(data, new TypeReference<>() {
                });
            } catch (JsonProcessingException e) {
                return fetchAndSaveFromFeedApi(startDate, endDate, size);
            }
        }

        return fetchAndSaveFromFeedApi(startDate, endDate, size);
    }

    @Override
    public NearEarthObjectDto getLookup(String id) {
        if (id == null) {
            throw new ValidationException("Id should not empty");
        }

        String data = redisTemplate.opsForValue().get(RedisConstant.REDIS_NEO_ID_KEY + id);
        if (data != null && !data.isEmpty()) {
            try {
                return objectMapper.readValue(data, NearEarthObjectDto.class);
            } catch (JsonProcessingException e) {
                return fetchAndSaveFromLookupApi(id);
            }
        }

        return fetchAndSaveFromLookupApi(id);
    }

    private NearEarthObjectDto fetchAndSaveFromLookupApi(String id) {
        NearEarthObjectDto response = externalNeoService.getLookup(id);

        if (response != null) {
            try {
                String key = RedisConstant.REDIS_NEO_ID_KEY + id;
                redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(response));
                redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
            } catch (JsonProcessingException e) {
                // do nothing, so it won't block the response
            }
        }

        return response;
    }

    private List<FeedDto> fetchAndSaveFromFeedApi(String startDate, String endDate, int size) {
        List<FeedDto> result = new ArrayList<>();
        FeedResponseDto response = externalNeoService.getFeed(startDate, endDate);

        if (response != null) {
            List<FeedDto> feeds = new ArrayList<>();
            response.nearEarthObjects().forEach((date, item) -> {
                item.stream().filter((itemKey) -> !itemKey.closeApproachData().isEmpty())
                        .forEach((itemKey) -> {
                            feeds.add(new FeedDto(
                                    itemKey.id(),
                                    itemKey.name(),
                                    date,
                                    itemKey.estimatedDiameter().kilometers().estimatedDiameterMin()
                                            + " - " +
                                            itemKey.estimatedDiameter().kilometers().estimatedDiameterMax()
                                            + "km",
                                    itemKey.closeApproachData().get(0).missDistance().kilometers()
                            ));
                        });
            });

            feeds.sort(Comparator.comparing(FeedDto::distanceInKm));
            result.addAll(feeds.subList(0, size));
        }
        try {
            String key = RedisConstant.REDIS_FEED_KEY + startDate + "_" + endDate + "_" + size;
            redisTemplate.opsForValue().set(
                    key, objectMapper.writeValueAsString(result)
            );
            redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        } catch (JsonProcessingException e) {
            // do nothing, so it won't block the response
        }

        return result;
    }
}
