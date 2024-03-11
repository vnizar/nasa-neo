package io.github.vnizar.app.service;

import io.github.vnizar.app.dto.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ExternalNeoServiceImplTest {

    @Mock
    private NetworkService networkService;

    @InjectMocks
    private ExternalNeoServiceImpl externalNeoService;

    @Test
    void testShouldReturnCorrectPayloadWhenSuccess() {
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
                            false,
                            List.of(new CloseApproachDataDto(
                                    new MissDistanceDto(
                                            "10",
                                            "10",
                                            "10",
                                            "10"
                                    )))
                    )
            ));
        }};
        FeedResponseDto feedResponseDto = new FeedResponseDto(neoList, 1);
        ResponseEntity<FeedResponseDto> mockResponse = new ResponseEntity<>(feedResponseDto, HttpStatus.OK);

        Mockito.when(networkService.get(Mockito.anyString(), Mockito.any())).thenReturn(
                mockResponse
        );

        FeedResponseDto response = externalNeoService.getFeed("2023-01-01", "2023-01-01");

        assertEquals(1, response.elementCount());
        assertEquals("name", response.nearEarthObjects().get("2022-01-01").get(0).name());
    }
}