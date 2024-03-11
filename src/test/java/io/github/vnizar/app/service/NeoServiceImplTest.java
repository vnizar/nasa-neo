package io.github.vnizar.app.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.vnizar.app.common.ValidationException;
import io.github.vnizar.app.dto.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class NeoServiceImplTest {

    @Mock
    private ExternalNeoService externalNeoService;

    @Mock
    private StringRedisTemplate stringRedisTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private NeoServiceImpl neoService;

    @Test
    void testServiceShouldReturnSuccess() {
        HashMap<String, List<NearEarthObjectDto>> neoList = generateDummyList();
        FeedResponseDto response = new FeedResponseDto(
                neoList,
                1
        );
        ValueOperations<String, String> valueOperationsMock = Mockito.mock(ValueOperations.class);
        Mockito.when(valueOperationsMock.get(Mockito.any())).thenReturn(null);
        Mockito.when(stringRedisTemplate.opsForValue()).thenReturn(valueOperationsMock);
        Mockito.when(externalNeoService.getFeed(Mockito.anyString(), Mockito.anyString())).thenReturn(
                response
        );


        List<FeedDto> feeds = neoService.getFeed("2022-01-01", "2022-01-01", 1);

        assertEquals(1, feeds.size());
        assertEquals("name", feeds.get(0).name());
    }

    @Test
    void testServiceShouldThrowValidationExceptionWhenDateIsInvalid() {
        assertThrows(ValidationException.class, () -> {
            neoService.getFeed(null, null, 10);
        });
    }

    @Test
    void testFeedShouldReturnEmptyWhenResponseIsNull() {
        ValueOperations<String, String> valueOperationsMock = Mockito.mock(ValueOperations.class);
        Mockito.when(valueOperationsMock.get(Mockito.any())).thenReturn(null);
        Mockito.when(stringRedisTemplate.opsForValue()).thenReturn(valueOperationsMock);
        Mockito.when(externalNeoService.getFeed(Mockito.anyString(), Mockito.anyString())).thenReturn(
                null
        );
        Mockito.when(stringRedisTemplate.opsForValue().get(Mockito.any())).thenReturn(null);

        List<FeedDto> feeds = neoService.getFeed("2022-01-01", "2022-01-01", 10);

        assertEquals(0, feeds.size());
    }

    private HashMap<String, List<NearEarthObjectDto>> generateDummyList() {
        return new HashMap<>() {{
            put("2022-01-01", List.of(
                    new NearEarthObjectDto(
                            "1",
                            "name",
                            new EstimatedDiameterDto(
                                    new EstimatedDiameterMinMaxDto(1.0f, 1.0f),
                                    new EstimatedDiameterMinMaxDto(1.0f, 1.0f),
                                    new EstimatedDiameterMinMaxDto(1.0f, 1.0f),
                                    new EstimatedDiameterMinMaxDto(1.0f, 1.0f)
                            ),
                            false,
                            List.of(new CloseApproachDataDto(
                                    new MissDistanceDto(
                                            "10",
                                            "10",
                                            "10",
                                            "10"
                                    ))
                            )
                    )
            ));
        }};
    }
}