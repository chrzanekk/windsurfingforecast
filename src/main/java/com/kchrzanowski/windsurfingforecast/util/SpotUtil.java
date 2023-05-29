package com.kchrzanowski.windsurfingforecast.util;

import com.kchrzanowski.windsurfingforecast.service.dto.ForecastDTO;
import com.kchrzanowski.windsurfingforecast.service.response.SpotResponse;

import java.util.*;
import java.util.stream.Collectors;


public class SpotUtil {

    private final Float minAvgTemperature;
    private final Float maxAvgTemperature;
    private final Float minWindSpeed;
    private final Float maxWindSpeed;

    public SpotUtil(Float minAvgTemperature, Float maxAvgTemperature, Float minWindSpeed, Float maxWindSpeed) {
        this.minAvgTemperature = minAvgTemperature;
        this.maxAvgTemperature = maxAvgTemperature;
        this.minWindSpeed = minWindSpeed;
        this.maxWindSpeed = maxWindSpeed;
    }

    private SpotUtil(Builder builder) {
        minAvgTemperature = builder.minAvgTemperature;
        maxAvgTemperature = builder.maxAvgTemperature;
        minWindSpeed = builder.minWindSpeed;
        maxWindSpeed = builder.maxWindSpeed;
    }

    public SpotResponse findBestSpot(Map<String, ForecastDTO> bestSpots) {
        if (bestSpots.entrySet().size() != 1) {
            return findSpotWithBestScoring(bestSpots);
        } else {
            Map.Entry<String, ForecastDTO> entry = bestSpots.entrySet().iterator().next();
            return SpotResponse.builder().spotName(entry.getKey()).forecastDTO(entry.getValue()).build();
        }
    }

    public <K, V extends Comparable<Float>> SpotResponse findSpotWithBestScoring(Map<String, ForecastDTO> bestSpotsWithScoring) {
        Optional<Map.Entry<String, ForecastDTO>> optionalMaxEntry = bestSpotsWithScoring.entrySet()
                .stream()
                .max(Comparator.comparing(entry -> entry.getValue().getSpotScoring()));
//        todo change to stream on optional
        if (optionalMaxEntry.isPresent()) {
            Map.Entry<String, ForecastDTO> maxEntry = optionalMaxEntry.get();
            Map<String, ForecastDTO> mapOfOnlyOneElementOrEmpty = findUniqueScoring(bestSpotsWithScoring);
            if (!mapOfOnlyOneElementOrEmpty.isEmpty()) {
                return SpotResponse.builder().spotName(maxEntry.getKey()).forecastDTO(maxEntry.getValue()).build();
            } else {
                return SpotResponse.builder().build();
            }
        } else {
            return SpotResponse.builder().build();
        }
    }

    private Map<String, ForecastDTO> findUniqueScoring(Map<String, ForecastDTO> spots) {
        Optional<Map.Entry<String, ForecastDTO>> optionalMaxEntry = spots.entrySet()
                .stream()
                .max(Comparator.comparing(entry -> entry.getValue().getSpotScoring()));
        Map<String, ForecastDTO> result = new HashMap<>();
        if (optionalMaxEntry.isPresent()) {
            ForecastDTO spotForecastDTO = optionalMaxEntry.get().getValue();
            for (Map.Entry<String, ForecastDTO> entry : spots.entrySet()) {
                if (isEntryScoringMatchWithMaxScoreSpot(spotForecastDTO, entry)) {
                    result.put(entry.getKey(), entry.getValue());
                }
            }
        }
        if (result.size() == 1) {
            return result;
        } else {
            return new HashMap<>();
        }
    }

    private static boolean isEntryScoringMatchWithMaxScoreSpot(ForecastDTO spotForecastDTO,
                                                               Map.Entry<String, ForecastDTO> entry) {
        return entry.getValue().getSpotScoring().compareTo(spotForecastDTO.getSpotScoring()) == 0;
    }


    public Map<String, ForecastDTO> calculateSpotsScoring(Map<String, ForecastDTO> spots) {
        spots.entrySet().forEach(entry ->
                entry.setValue(
                        ForecastDTO.builder(entry.getValue()).spotScoring(calculateSpotScoring(entry.getValue())).build()));
        return spots;
    }

    private Float calculateSpotScoring(ForecastDTO forecastDTO) {
        return (forecastDTO.getWindSpeed() * 3f) + forecastDTO.getAverageTemperature();
    }

    public Map<String, ForecastDTO> findSpotsWithMatchingForecastRequirements(Map<String, ForecastDTO> allSpots) {
        return allSpots.entrySet().stream()
                .filter(forecast -> checkIfForecastIsGoodForSurfing(forecast.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private boolean checkIfForecastIsGoodForSurfing(ForecastDTO forecastDTO) {
        return checkIfAverageTemperatureIsGoodForSurfing(forecastDTO.getAverageTemperature())
                && checkIfWindSpeedIsGoodForSurfing(forecastDTO.getWindSpeed());
    }

    private boolean checkIfAverageTemperatureIsGoodForSurfing(Float temperature) {
        return checkIfValueIsOutOfGivenRange(temperature, minAvgTemperature, maxAvgTemperature);
    }

    private boolean checkIfWindSpeedIsGoodForSurfing(Float temperature) {
        return checkIfValueIsOutOfGivenRange(temperature, minWindSpeed, maxWindSpeed);
    }

    private boolean checkIfValueIsOutOfGivenRange(Float value, Float firstGivenValue, Float secondGivenValue) {
        return value.compareTo(firstGivenValue) < 0 || value.compareTo(secondGivenValue) > 0;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpotUtil spotUtil = (SpotUtil) o;

        if (!Objects.equals(minAvgTemperature, spotUtil.minAvgTemperature))
            return false;
        if (!Objects.equals(maxAvgTemperature, spotUtil.maxAvgTemperature))
            return false;
        if (!Objects.equals(minWindSpeed, spotUtil.minWindSpeed))
            return false;
        return Objects.equals(maxWindSpeed, spotUtil.maxWindSpeed);
    }

    @Override
    public int hashCode() {
        int result = minAvgTemperature != null ? minAvgTemperature.hashCode() : 0;
        result = 31 * result + (maxAvgTemperature != null ? maxAvgTemperature.hashCode() : 0);
        result = 31 * result + (minWindSpeed != null ? minWindSpeed.hashCode() : 0);
        result = 31 * result + (maxWindSpeed != null ? maxWindSpeed.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SpotUtil{" +
                "minAvgTemperature=" + minAvgTemperature +
                ", maxAvgTemperature=" + maxAvgTemperature +
                ", minWindSpeed=" + minWindSpeed +
                ", maxWindSpeed=" + maxWindSpeed +
                '}';
    }


    public static final class Builder {
        private Float minAvgTemperature;
        private Float maxAvgTemperature;
        private Float minWindSpeed;
        private Float maxWindSpeed;

        private Builder() {
        }

        public Builder minAvgTemperature(Float minAvgTemperature) {
            this.minAvgTemperature = minAvgTemperature;
            return this;
        }

        public Builder maxAvgTemperature(Float maxAvgTemperature) {
            this.maxAvgTemperature = maxAvgTemperature;
            return this;
        }

        public Builder minWindSpeed(Float minWindSpeed) {
            this.minWindSpeed = minWindSpeed;
            return this;
        }

        public Builder maxWindSpeed(Float maxWindSpeed) {
            this.maxWindSpeed = maxWindSpeed;
            return this;
        }

        public SpotUtil build() {
            return new SpotUtil(this);
        }
    }
}
