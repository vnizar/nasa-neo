package io.github.vnizar.app.controller;

import io.github.vnizar.app.dto.FeedDto;
import io.github.vnizar.app.service.NeoServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            String endDate
    ) {
        return ResponseEntity.ok(neoService.getFeed(startDate, endDate));
    }
}
