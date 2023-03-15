package com.kchrzanowski.windsurfingforecast.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Objects;

public class ForecastDTO {

    private Float windSpeed;
    private Float averageTemperature;
    private LocalDate dateTime;

    private Float spotScoring;

    public ForecastDTO(@JsonProperty("wind_spd") Float windSpeed,@JsonProperty("temp") Float averageTemperature,
                       @JsonProperty("datetime") LocalDate dateTime) {
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
        spotScoring = builder.spotScoring;
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

    public Float getSpotScoring() {
        return spotScoring;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ForecastDTO that = (ForecastDTO) o;

        if (!Objects.equals(windSpeed, that.windSpeed)) return false;
        if (!Objects.equals(averageTemperature, that.averageTemperature))
            return false;
        if (!Objects.equals(dateTime, that.dateTime)) return false;
        return Objects.equals(spotScoring, that.spotScoring);
    }

    @Override
    public int hashCode() {
        int result = windSpeed != null ? windSpeed.hashCode() : 0;
        result = 31 * result + (averageTemperature != null ? averageTemperature.hashCode() : 0);
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        result = 31 * result + (spotScoring != null ? spotScoring.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ForecastDTO{" +
                "windSpeed=" + windSpeed +
                ", averageTemperature=" + averageTemperature +
                ", dateTime=" + dateTime +
                ", spotScoring=" + spotScoring +
                '}';
    }


    public static final class Builder {
        private Float windSpeed;
        private Float averageTemperature;
        private LocalDate dateTime;
        private Float spotScoring;

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

        public Builder spotScoring(Float spotScoring) {
            this.spotScoring = spotScoring;
            return this;
        }

        public ForecastDTO build() {
            return new ForecastDTO(this);
        }
    }
}
