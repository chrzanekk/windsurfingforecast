package com.kchrzanowski.windsurfingforecast.service.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kchrzanowski.windsurfingforecast.service.dto.ForecastDTO;

import java.util.List;
import java.util.Objects;

public class ForecastResponse {

    private final String cityName;
    private final List<ForecastDTO> data;

    public ForecastResponse(@JsonProperty("city_name") String cityName, @JsonProperty("data") List<ForecastDTO> data) {
        this.cityName = cityName;
        this.data = data;
    }

    private ForecastResponse(Builder builder) {
        cityName = builder.cityName;
        data = builder.data;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getCityName() {
        return cityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ForecastResponse that = (ForecastResponse) o;

        if (!Objects.equals(cityName, that.cityName)) return false;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        int result = cityName != null ? cityName.hashCode() : 0;
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    public List<ForecastDTO> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "ForecastResponse{" +
                "cityName='" + cityName + '\'' +
                ", data=" + data +
                '}';
    }


    public static final class Builder {
        private String cityName;
        private List<ForecastDTO> data;

        private Builder() {
        }

        public Builder cityName(String cityName) {
            this.cityName = cityName;
            return this;
        }

        public Builder data(List<ForecastDTO> data) {
            this.data = data;
            return this;
        }

        public ForecastResponse build() {
            return new ForecastResponse(this);
        }
    }
}
