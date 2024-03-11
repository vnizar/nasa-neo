package io.github.vnizar.app.controller;

import io.github.vnizar.app.dto.FeedDto;
import io.github.vnizar.app.dto.NearEarthObjectDto;
import io.github.vnizar.app.service.NeoServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/v1")
public class NeoController {

    @Autowired
    private NeoServiceImpl neoService;

    @GetMapping("/feed")
    public ResponseEntity<List<FeedDto>> getFeed(
            @RequestParam(name = "start_date")
            @DateTimeFormat(pattern = "dd-MM-yyyy")
            @NotNull
            String startDate,

            @RequestParam(name = "end_date")
            @DateTimeFormat(pattern = "dd-MM-yyyy")
            @NotNull
            String endDate,

            @RequestParam(name = "size", defaultValue = "10")
            int size
    ) {
        return ResponseEntity.ok(neoService.getFeed(startDate, endDate, size));
    }

    @GetMapping("/lookup/{id}")
    public ResponseEntity<NearEarthObjectDto> getLookup(
            @PathVariable(value = "id")
            @NotNull
            String id
    ) {
        return ResponseEntity.ok(neoService.getLookup(id));
    }
}
