package com.kchrzanowski.windsurfingforecast.service.dto.response;

import com.kchrzanowski.windsurfingforecast.service.dto.ForecastDTO;

import java.util.Objects;

public class SpotResponse {

    private String spotName;
    private ForecastDTO forecastDTO;

    private SpotResponse(Builder builder) {
        spotName = builder.spotName;
        forecastDTO = builder.forecastDTO;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getSpotName() {
        return spotName;
    }

    public ForecastDTO getForecastDTO() {
        return forecastDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpotResponse that = (SpotResponse) o;

        if (!Objects.equals(spotName, that.spotName)) return false;
        return Objects.equals(forecastDTO, that.forecastDTO);
    }

    @Override
    public int hashCode() {
        int result = spotName != null ? spotName.hashCode() : 0;
        result = 31 * result + (forecastDTO != null ? forecastDTO.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SpotResponse{" +
                "spotName='" + spotName + '\'' +
                ", forecastDTO=" + forecastDTO +
                '}';
    }


    public static final class Builder {
        private String spotName;
        private ForecastDTO forecastDTO;

        private Builder() {
        }

        public Builder spotName(String spotName) {
            this.spotName = spotName;
            return this;
        }

        public Builder forecastDTO(ForecastDTO forecastDTO) {
            this.forecastDTO = forecastDTO;
            return this;
        }

        public SpotResponse build() {
            return new SpotResponse(this);
        }
    }
}
