package com.kchrzanowski.windsurfingforecast.controller;

import com.kchrzanowski.windsurfingforecast.service.SpotService;
import com.kchrzanowski.windsurfingforecast.service.dto.SpotDTO;
import com.kchrzanowski.windsurfingforecast.service.dto.response.SpotResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/api/forecast")
public class SpotController {
    private final Logger log = LoggerFactory.getLogger(SpotController.class);

    private final SpotService spotService;

    public SpotController(SpotService spotService) {
        this.spotService = spotService;
    }

    @GetMapping(path = "/all")
    public ResponseEntity<SpotResponse> getBestSurfingSpots(@RequestParam(value = "date")
                                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                 LocalDate date ) {
        log.debug("REST request to get best spots for windsurfers for date: {}", date.toString());
        SpotResponse spotResponse = spotService.getBestSpots(date);
        return ResponseEntity.ok().body(spotResponse);
    }

}
