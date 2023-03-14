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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SpotServiceImpl implements SpotService {

    private static final String SPOT_NOT_FOUND = "Spot not found";
    private static final Float MIN_AVG_TEMPERATURE = 5f;
    private static final Float MAX_AVG_TEMPERATURE = 35f;
    private static final Float MIN_WIND_SPEED = 5f;
    private static final Float MAX_WIND_SPEED = 35f;

    private final Logger log = LoggerFactory.getLogger(SpotServiceImpl.class);

    private final SpotRepository spotRepository;
    private final SpotMapper spotMapper;

    private final ForecastService forecastService;

    public SpotServiceImpl(SpotRepository spotRepository, SpotMapper spotMapper, ForecastService forecastService) {
        this.spotRepository = spotRepository;
        this.spotMapper = spotMapper;
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
        Map<String, ForecastDTO> bestSpots = findSpotsWithMatchingForecastRequirements(allSpotsByDate);
        return calculateBestSpot(bestSpots);
    }

    private SpotResponse calculateBestSpot(Map<String, ForecastDTO> bestSpots) {
        if (bestSpots.entrySet().size() != 1) {
            return handleOneBestSpot(bestSpots);
        } else {
            Map.Entry<String, ForecastDTO> entry = bestSpots.entrySet().iterator().next();
            String spotName = entry.getKey();
            ForecastDTO spotForecastDTO = entry.getValue();
            return SpotResponse.builder().spotName(spotName).forecastDTO(spotForecastDTO).build();
        }
    }

    private SpotResponse handleOneBestSpot(Map<String, ForecastDTO> bestSpots) {
        Map<String, ForecastDTO> bestSpotsWithScoring = calculateSpotsScoring(bestSpots);
        return findSpotWithBestScoring(bestSpotsWithScoring);
    }

    private <K, V extends Comparable<Float>> SpotResponse findSpotWithBestScoring(Map<String, ForecastDTO> bestSpotsWithScoring) {
       Optional<Map.Entry<String, ForecastDTO>> optionalMaxEntry = bestSpotsWithScoring.entrySet()
               .stream()
               .max(Comparator.comparing(entry -> entry.getValue().getSpotScoring()));
       if(optionalMaxEntry.isPresent()) {
            Map.Entry<String, ForecastDTO> maxEntry = optionalMaxEntry.get();
           return SpotResponse.builder().spotName(maxEntry.getKey()).forecastDTO(maxEntry.getValue()).build();
       } else {
           throw new SpotNotFoundException("Best spot not found");
       }
    }

    private Map<String, ForecastDTO> calculateSpotsScoring(Map<String, ForecastDTO> bestSpots) {
        bestSpots.entrySet().forEach(entry ->
                entry.setValue(
                        ForecastDTO.builder()
                                .averageTemperature(entry.getValue().getAverageTemperature())
                                .windSpeed(entry.getValue().getWindSpeed())
                                .dateTime(entry.getValue().getDateTime())
                                .spotScoring(calculateSpotScoring(entry.getValue())).build()));
        return bestSpots;
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

    private Map<String, ForecastDTO> findSpotsWithMatchingForecastRequirements(Map<String, ForecastDTO> allSpots) {
        return allSpots.entrySet().stream()
                .filter(forecast -> checkIfForecastIsGoodForSurfing(forecast.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private boolean checkIfForecastIsGoodForSurfing(ForecastDTO forecastDTO) {
        return checkIfAverageTemperatureIsGoodForSurfing(forecastDTO.getAverageTemperature()) && checkIfWindSpeedIsGoodForSurfing(forecastDTO.getWindSpeed());
    }

    private boolean checkIfAverageTemperatureIsGoodForSurfing(Float temperature) {
        return checkIfValueIsOutOfGivenRange(temperature, MIN_AVG_TEMPERATURE, MAX_AVG_TEMPERATURE);
    }

    private boolean checkIfWindSpeedIsGoodForSurfing(Float temperature) {
        return checkIfValueIsOutOfGivenRange(temperature, MIN_WIND_SPEED, MAX_WIND_SPEED);
    }

    private boolean checkIfValueIsOutOfGivenRange(Float value, Float firstGivenValue, Float secondGivenValue) {
        return value.compareTo(firstGivenValue) < 0 && value.compareTo(secondGivenValue) > 0;
    }

    private Float calculateSpotScoring(ForecastDTO forecastDTO) {
        return (forecastDTO.getWindSpeed() * 3f) + forecastDTO.getAverageTemperature();
    }


}
