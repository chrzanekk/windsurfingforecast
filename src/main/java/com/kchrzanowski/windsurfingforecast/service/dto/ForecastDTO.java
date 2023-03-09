package com.kchrzanowski.windsurfingforecast.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Objects;

public class ForecastDTO {

    private Float windSpeed;
    private Float averageTemperature;
    private LocalDate dateTime;

    public ForecastDTO(@JsonProperty("wind_spd") Float windSpeed,@JsonProperty("temp") Float averageTemperature,
                       @JsonProperty("localDate") LocalDate dateTime) {
        this.windSpeed = windSpeed;
        this.averageTemperature = averageTemperature;
        this.dateTime = dateTime;
    }

    public ForecastDTO() {
    }

    private ForecastDTO(Builder builder) {
        windSpeed = builder.windSpeed;
        averageTemperature = builder.averageTemperature;
        dateTime = builder.dateTime;
    }

    public static Builder builder() {
        return new Builder();
    }


    public Float getWindSpeed() {
        return windSpeed;
    }

    public Float getAverageTemperature() {
        return averageTemperature;
    }

    public LocalDate getDateTime() {
        return dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ForecastDTO that = (ForecastDTO) o;

        if (!Objects.equals(windSpeed, that.windSpeed)) return false;
        return Objects.equals(averageTemperature, that.averageTemperature);
    }

    @Override
    public int hashCode() {
        int result = windSpeed != null ? windSpeed.hashCode() : 0;
        result = 31 * result + (averageTemperature != null ? averageTemperature.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ForecastDTO{" +
                "windSpeed=" + windSpeed +
                ", averageTemperature=" + averageTemperature +
                '}';
    }


    public static final class Builder {
        private Float windSpeed;
        private Float averageTemperature;
        private LocalDate dateTime;

        private Builder() {
        }

        public Builder windSpeed(Float windSpeed) {
            this.windSpeed = windSpeed;
            return this;
        }

        public Builder averageTemperature(Float averageTemperature) {
            this.averageTemperature = averageTemperature;
            return this;
        }

        public Builder dateTime(LocalDate dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public ForecastDTO build() {
            return new ForecastDTO(this);
        }
    }
}
