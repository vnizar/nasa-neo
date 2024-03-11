package io.github.vnizar.app.service;

import io.github.vnizar.app.common.ValidationException;
import io.github.vnizar.app.dto.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class NeoServiceImplTest {

    @Mock
    private ExternalNeoService externalNeoService;

    @InjectMocks
    private NeoServiceImpl neoService;

    @Test
    void testServiceShouldReturnSuccess() {
        HashMap<String, List<NearEarthObjectDto>> neoList = new HashMap<>() {{
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
                            false)
            ));
        }};
        FeedResponseDto response = new FeedResponseDto(
                neoList,
                1
        );
        Mockito.when(externalNeoService.getFeed(Mockito.anyString(), Mockito.anyString())).thenReturn(
                response
        );

        List<FeedDto> feeds = neoService.getFeed("2022-01-01", "2022-01-01");

        assertEquals(1, feeds.size());
        assertEquals("name", feeds.get(0).name());
    }

    @Test
    void testServiceShouldThrowValidationExceptionWhenDateIsInvalid() {
        assertThrows(ValidationException.class, () -> {
            neoService.getFeed(null, null);
        });
    }

    @Test
    void testServiceShouldReturnEmptyWhenResponseIsNull() {
        Mockito.when(externalNeoService.getFeed(Mockito.anyString(), Mockito.anyString())).thenReturn(
                null
        );

        List<FeedDto> feeds = neoService.getFeed("2022-01-01", "2022-01-01");

        assertEquals(0, feeds.size());
    }
}