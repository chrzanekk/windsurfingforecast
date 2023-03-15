package com.kchrzanowski.windsurfingforecast.service.impl;

import com.kchrzanowski.windsurfingforecast.exception.ForecastNotFoundException;
import com.kchrzanowski.windsurfingforecast.service.ForecastService;
import com.kchrzanowski.windsurfingforecast.service.dto.ForecastDTO;
import com.kchrzanowski.windsurfingforecast.service.dto.SpotDTO;
import com.kchrzanowski.windsurfingforecast.service.response.ForecastResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ForecastServiceImpl implements ForecastService {

    @Value("${forecast.baseApiUrl}")
    private String apiUrl;
    @Value("${forecast.apiKey}")
    private String apiKey;


    private final RestTemplate restTemplate;

    private final Logger log = LoggerFactory.getLogger(ForecastServiceImpl.class);

    public ForecastServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ForecastResponse getForecast(SpotDTO spotDTO, LocalDate localDate) {
        log.debug("Request to get forecast for spot by date: {}{}", spotDTO, localDate);
        ForecastResponse forecastResponse = getForecastList(spotDTO);
        String spotName = forecastResponse.getCityName();
        List<ForecastDTO> forecastDTOList = forecastResponse.getData();
        List<ForecastDTO> result =
                forecastDTOList.stream().filter(forecastDTO -> forecastDTO.getDateTime().isEqual(localDate)
                ).toList();
        if (!result.isEmpty()) {
            return ForecastResponse.builder().cityName(forecastResponse.getCityName()).data(result).build();
        } else {
            throw new ForecastNotFoundException("Forecast not found or wrong date: " + localDate.toString());
        }
    }

    private ForecastResponse getForecastList(SpotDTO spotDTO) {
        String uri = prepareUri(spotDTO);
        Map<String, Object> params = prepareParams(spotDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        HttpEntity<ForecastResponse> response = restTemplate.exchange(uri, HttpMethod.GET, entity,
                ForecastResponse.class, params);
        return response.getBody();
    }

    private String prepareUri(SpotDTO spotDTO) {
        Double latitude = spotDTO.getLatitude();
        Double longitude = spotDTO.getLongitude();


        return UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("lat", latitude.toString())
                .queryParam("lon", longitude.toString())
                .queryParam("key", apiKey)
                .encode()
                .toUriString();
    }

    private Map<String, Object> prepareParams(SpotDTO spotDTO) {
        Double latitude = spotDTO.getLatitude();
        Double longitude = spotDTO.getLongitude();

        Map<String, Object> params = new HashMap<>();
        params.put("lat", latitude);
        params.put("lon", longitude);
        params.put("key", apiKey);

        return params;
    }
}
