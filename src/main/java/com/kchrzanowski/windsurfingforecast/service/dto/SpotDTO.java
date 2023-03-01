package com.kchrzanowski.windsurfingforecast.service.dto;

import java.util.Objects;

public class SpotDTO {

    private final Long id;
    private final String name;
    private final Double latitude;
    private final Double longitude;

    public SpotDTO(Long id, String name, Double latitude, Double longitude) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    private SpotDTO(Builder builder) {
        id = builder.id;
        name = builder.name;
        latitude = builder.latitude;
        longitude = builder.longitude;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpotDTO spotDTO = (SpotDTO) o;

        if (!Objects.equals(id, spotDTO.id)) return false;
        if (!Objects.equals(name, spotDTO.name)) return false;
        if (!Objects.equals(latitude, spotDTO.latitude)) return false;
        return Objects.equals(longitude, spotDTO.longitude);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SpotDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    public static final class Builder {
        private Long id;
        private String name;
        private Double latitude;
        private Double longitude;

        private Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder latitude(Double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder longitude(Double longitude) {
            this.longitude = longitude;
            return this;
        }

        public SpotDTO build() {
            return new SpotDTO(this);
        }
    }
}
