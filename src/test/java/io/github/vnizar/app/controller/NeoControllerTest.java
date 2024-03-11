package io.github.vnizar.app.controller;

import io.github.vnizar.app.dto.FeedDto;
import io.github.vnizar.app.service.NeoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class NeoControllerTest {

    @Mock
    private NeoServiceImpl neoService;

    @InjectMocks
    private NeoController neoController;

    @Test
    void testShouldReturnCorrectResponseWhenSuccess() {
        List<FeedDto> mockData = List.of(
                new FeedDto("1", "name1", "2023-01-01", "1 - 2 km"),
                new FeedDto("2", "name2", "2023-01-01", "1 - 2 km"),
                new FeedDto("3", "name3", "2023-01-01", "1 - 2 km")
        );

        Mockito.when(neoService.getFeed(Mockito.anyString(), Mockito.anyString())).thenReturn(mockData);

        ResponseEntity<List<FeedDto>> response = neoController.getFeed("2023-01-01", "2023-01-01");

        assertEquals(3, response.getBody().size());
    }
}