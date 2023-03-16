package com.kchrzanowski.windsurfingforecast.service;

import com.kchrzanowski.windsurfingforecast.WindsurfingforecastApplication;
import com.kchrzanowski.windsurfingforecast.service.dto.ForecastDTO;
import com.kchrzanowski.windsurfingforecast.service.response.SpotResponse;
import com.kchrzanowski.windsurfingforecast.util.SpotUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = WindsurfingforecastApplication.class)
public class SpotUtilTest {

    private static final String FIRST_SPOT_NAME = "Jastarnia";
    private static final String SECOND_SPOT_NAME = "Bridgetown";

    @Test
    public void calculateBestSpotShouldFindOne() {
        SpotUtil spotUtil = SpotUtil.builder()
                .minAvgTemperature(2f)
                .maxAvgTemperature(10f)
                .minWindSpeed(2f)
                .maxWindSpeed(10f).build();

        ForecastDTO firstDTO = ForecastDTO.builder()
                .averageTemperature(3f)
                .windSpeed(4f)
                .spotScoring(calculateSpotScoring(4f,3f)).build();
        ForecastDTO secondDTO = ForecastDTO.builder()
                .averageTemperature(1f)
                .windSpeed(11f)
                .spotScoring(calculateSpotScoring(11f,1f)).build();

        Map<String, ForecastDTO> spots = new HashMap<>();
        spots.put(FIRST_SPOT_NAME, firstDTO);
        spots.put(SECOND_SPOT_NAME, secondDTO);
        Map<String, ForecastDTO> spotsWithScoring = spotUtil.calculateSpotsScoring(spots);

        SpotResponse expectedSpotResponse = SpotResponse.builder()
                .spotName(SECOND_SPOT_NAME)
                .forecastDTO(secondDTO).build();

        SpotResponse result = spotUtil.calculateBestSpot(spotsWithScoring);

        assertThat(result).isEqualTo(expectedSpotResponse);
    }
    @Test
    public void findSpotWithBestScoringAndShouldFindOne() {
        SpotUtil spotUtil = SpotUtil.builder()
                .minAvgTemperature(2f)
                .maxAvgTemperature(10f)
                .minWindSpeed(2f)
                .maxWindSpeed(10f).build();

        ForecastDTO firstDTO = ForecastDTO.builder()
                .averageTemperature(3f)
                .windSpeed(4f)
                .spotScoring(calculateSpotScoring(4f,3f)).build();
        ForecastDTO secondDTO = ForecastDTO.builder()
                .averageTemperature(1f)
                .windSpeed(11f)
                .spotScoring(calculateSpotScoring(11f,1f)).build();

        Map<String, ForecastDTO> spots = new HashMap<>();
        spots.put(FIRST_SPOT_NAME, firstDTO);
        spots.put(SECOND_SPOT_NAME, secondDTO);
        Map<String, ForecastDTO> spotsWithScoring = spotUtil.calculateSpotsScoring(spots);

        SpotResponse expectedSpotResponse = SpotResponse.builder()
                .spotName(SECOND_SPOT_NAME)
                .forecastDTO(secondDTO).build();

        SpotResponse result = spotUtil.findSpotWithBestScoring(spots);

        assertThat(result).isEqualTo(expectedSpotResponse);
    }
    @Test
    public void findSpotWithBestScoringAndShouldReturnEmptyResponse() {
        SpotUtil spotUtil = SpotUtil.builder()
                .minAvgTemperature(2f)
                .maxAvgTemperature(10f)
                .minWindSpeed(2f)
                .maxWindSpeed(10f).build();

        ForecastDTO firstDTO = ForecastDTO.builder()
                .averageTemperature(1f)
                .windSpeed(1f)
                .spotScoring(calculateSpotScoring(1f,1f)).build();
        ForecastDTO secondDTO = ForecastDTO.builder()
                .averageTemperature(1f)
                .windSpeed(1f)
                .spotScoring(calculateSpotScoring(1f,1f)).build();

        Map<String, ForecastDTO> spots = new HashMap<>();
        spots.put(FIRST_SPOT_NAME, firstDTO);
        spots.put(SECOND_SPOT_NAME, secondDTO);
        Map<String, ForecastDTO> spotsWithScoring = spotUtil.calculateSpotsScoring(spots);

        SpotResponse expectedSpotResponse = SpotResponse.builder().build();

        SpotResponse result = spotUtil.findSpotWithBestScoring(spots);

        assertThat(result).isEqualTo(expectedSpotResponse);
    }
    @Test
    public void calculateSpotScoring() {
        SpotUtil spotUtil = SpotUtil.builder()
                .minAvgTemperature(2f)
                .maxAvgTemperature(10f)
                .minWindSpeed(2f)
                .maxWindSpeed(10f).build();

        ForecastDTO firstDTO = ForecastDTO.builder()
                .averageTemperature(3f)
                .windSpeed(4f)
                .spotScoring(calculateSpotScoring(4f,3f)).build();
        ForecastDTO secondDTO = ForecastDTO.builder()
                .averageTemperature(1f)
                .windSpeed(11f)
                .spotScoring(calculateSpotScoring(11f,1f)).build();

        Map<String, ForecastDTO> spots = new HashMap<>();
        spots.put(FIRST_SPOT_NAME, firstDTO);
        spots.put(SECOND_SPOT_NAME, secondDTO);

        Map<String, ForecastDTO> spotsWithScoring = spotUtil.calculateSpotsScoring(spots);
        ForecastDTO firstSpot = spotsWithScoring.get(FIRST_SPOT_NAME);
        ForecastDTO secondSpot = spotsWithScoring.get(SECOND_SPOT_NAME);

        Float firstScoring = calculateSpotScoring(firstDTO.getWindSpeed(),firstDTO.getAverageTemperature());
        Float secondScoring = calculateSpotScoring(secondDTO.getWindSpeed(),secondDTO.getAverageTemperature());

        assertThat(firstSpot.getSpotScoring()).isEqualTo(firstScoring);
        assertThat(secondSpot.getSpotScoring()).isEqualTo(secondScoring);
    }
    @Test
    public void findSpotsWithMatchingForecastAndShouldFoundOne() {
        SpotUtil spotUtil = SpotUtil.builder()
                .minAvgTemperature(2f)
                .maxAvgTemperature(10f)
                .minWindSpeed(2f)
                .maxWindSpeed(10f).build();

        ForecastDTO firstDTO = ForecastDTO.builder()
                .averageTemperature(3f)
                .windSpeed(3f)
                .spotScoring(calculateSpotScoring(3f,3f)).build();
        ForecastDTO secondDTO = ForecastDTO.builder()
                .averageTemperature(1f)
                .windSpeed(11f)
                .spotScoring(calculateSpotScoring(11f,1f)).build();

        Map<String, ForecastDTO> spots = new HashMap<>();
        spots.put(FIRST_SPOT_NAME, firstDTO);
        spots.put(SECOND_SPOT_NAME, secondDTO);

        int expectedSize = 1;

        Map<String, ForecastDTO> matchingSpots = spotUtil.findSpotsWithMatchingForecastRequirements(spots);
        int sizeOfMatchingSpots = matchingSpots.size();
        Map.Entry<String, ForecastDTO> entry = matchingSpots.entrySet().iterator().next();
        String spotName = entry.getKey();
        ForecastDTO spotForecastDTO = entry.getValue();

        assertThat(sizeOfMatchingSpots).isEqualTo(expectedSize);
        assertThat(spotName).isEqualTo(SECOND_SPOT_NAME);
        assertThat(spotForecastDTO).isEqualTo(secondDTO);
    }
    @Test
    public void findSpotsWithMatchingForecastAndShouldReturnEmptyMap() {
        SpotUtil spotUtil = SpotUtil.builder()
                .minAvgTemperature(2f)
                .maxAvgTemperature(10f)
                .minWindSpeed(2f)
                .maxWindSpeed(10f).build();

        ForecastDTO firstDTO = ForecastDTO.builder()
                .averageTemperature(3f)
                .windSpeed(3f)
                .spotScoring(calculateSpotScoring(3f,3f)).build();
        ForecastDTO secondDTO = ForecastDTO.builder()
                .averageTemperature(3f)
                .windSpeed(3f)
                .spotScoring(calculateSpotScoring(3f,3f)).build();

        Map<String, ForecastDTO> spots = new HashMap<>();
        spots.put(FIRST_SPOT_NAME, firstDTO);
        spots.put(SECOND_SPOT_NAME, secondDTO);

        int expectedSize = 0;

        Map<String, ForecastDTO> matchingSpots = spotUtil.findSpotsWithMatchingForecastRequirements(spots);
        int sizeOfMatchingSpots = matchingSpots.size();
        assertThat(sizeOfMatchingSpots).isEqualTo(expectedSize);

    }

    private Float calculateSpotScoring(Float windSpeed, Float avgTemp) {
        return (windSpeed * 3f) + avgTemp;
    }

}
