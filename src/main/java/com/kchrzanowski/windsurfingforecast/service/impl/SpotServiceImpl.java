package com.kchrzanowski.windsurfingforecast.service.impl;

import com.kchrzanowski.windsurfingforecast.domain.Spot;
import com.kchrzanowski.windsurfingforecast.exception.SpotNotFoundException;
import com.kchrzanowski.windsurfingforecast.repository.SpotRepository;
import com.kchrzanowski.windsurfingforecast.service.ForecastService;
import com.kchrzanowski.windsurfingforecast.service.SpotService;
import com.kchrzanowski.windsurfingforecast.service.dto.ForecastDTO;
import com.kchrzanowski.windsurfingforecast.service.dto.SpotDTO;
import com.kchrzanowski.windsurfingforecast.service.mapper.SpotMapper;
import com.kchrzanowski.windsurfingforecast.service.response.ForecastResponse;
import com.kchrzanowski.windsurfingforecast.service.response.SpotResponse;
import com.kchrzanowski.windsurfingforecast.util.SpotUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SpotServiceImpl implements SpotService {

    private static final String SPOT_NOT_FOUND = "Spot not found";
    private static final Float MIN_AVG_TEMPERATURE = 10f;
    private static final Float MAX_AVG_TEMPERATURE = 15f;
    private static final Float MIN_WIND_SPEED = 10f;
    private static final Float MAX_WIND_SPEED = 18f;

    private final Logger log = LoggerFactory.getLogger(SpotServiceImpl.class);

    private final SpotRepository spotRepository;
    private final SpotMapper spotMapper;
    private final SpotUtil spotUtil;
    private final ForecastService forecastService;

    public SpotServiceImpl(SpotRepository spotRepository, SpotMapper spotMapper, ForecastService forecastService) {
        this.spotRepository = spotRepository;
        this.spotMapper = spotMapper;
        this.spotUtil = SpotUtil.builder().
                        minAvgTemperature(MIN_AVG_TEMPERATURE).
                        maxAvgTemperature(MAX_AVG_TEMPERATURE).
                        minWindSpeed(MIN_WIND_SPEED).
                        maxWindSpeed(MAX_WIND_SPEED).build();
        this.forecastService = forecastService;
    }

    @Override
    public List<SpotDTO> getAllSpots() {
        log.debug("Get all spots.");
        return spotMapper.toDto(spotRepository.findAll());
    }

    @Override
    public SpotDTO getSpotById(Long id) {
        log.debug("Find spot by id: {}", id);
        Optional<Spot> optionalSpot = spotRepository.findById(id);
        return spotMapper.toDto(optionalSpot.orElseThrow(() -> new SpotNotFoundException(SPOT_NOT_FOUND + ": " + id)));
    }

    @Override
    public SpotDTO addNew(SpotDTO spotDTO) {
        log.debug("Add new spot : {}", spotDTO);
        return spotMapper.toDto(spotRepository.save(spotMapper.toEntity(spotDTO)));
    }

    @Override
    public SpotDTO update(SpotDTO spotDTO) {
        log.debug("Update spot : {}", spotDTO);
        return spotMapper.toDto(spotRepository.save(spotMapper.toEntity(spotDTO)));
    }

    @Override
    public void delete(Long id) {
        log.debug("Delete spot : {}", id);
        spotRepository.deleteSpotById(id);
    }

    @Override
    public SpotResponse getBestSpot(LocalDate date) {
        Map<String, ForecastDTO> allSpotsByDate = getAllSpotsByDate(date);
        Map<String, ForecastDTO> allSpotsWithScoring = spotUtil.calculateSpotsScoring(allSpotsByDate);
        Map<String, ForecastDTO> bestSpots = spotUtil.findSpotsWithMatchingForecastRequirements(allSpotsByDate);
        return spotUtil.calculateBestSpot(bestSpots);
    }

    private Map<String, ForecastDTO> getAllSpotsByDate(LocalDate date) {
        List<SpotDTO> spotDTOList = spotMapper.toDto(spotRepository.findAll());
        return convertListOfForecastToMap(spotDTOList.stream().map(spotDTO -> forecastService.getForecast(spotDTO,
                date)).toList());
    }

    private Map<String, ForecastDTO> convertListOfForecastToMap(List<ForecastResponse> forecastResponses) {
        return forecastResponses.stream().collect(Collectors.toMap(ForecastResponse::getCityName,
                forecastResponse -> forecastResponse.getData().get(0)));
    }

}
