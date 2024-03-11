package io.github.vnizar.app.service;

import io.github.vnizar.app.dto.EstimatedDiameterDto;
import io.github.vnizar.app.dto.EstimatedDiameterMinMaxDto;
import io.github.vnizar.app.dto.FeedResponseDto;
import io.github.vnizar.app.dto.NearEarthObjectDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class NetworkServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private NetworkServiceImpl networkService;

    @Test
    void testShouldReturnCorrectObjectWhenSuccess() {
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
        FeedResponseDto feedResponseDto = new FeedResponseDto(neoList, 1);
        ResponseEntity<FeedResponseDto> mockResponse = new ResponseEntity<>(feedResponseDto, HttpStatus.OK);

        Mockito.when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<FeedResponseDto>>any()
        )).thenReturn(mockResponse);

        ResponseEntity<FeedResponseDto> response = networkService.get("https://abc", FeedResponseDto.class);

        assertEquals(1, response.getBody().nearEarthObjects().size());
    }
}