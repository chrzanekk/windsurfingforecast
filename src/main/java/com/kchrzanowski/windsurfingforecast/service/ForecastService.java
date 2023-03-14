package com.kchrzanowski.windsurfingforecast.service;

import com.kchrzanowski.windsurfingforecast.service.dto.SpotDTO;
import com.kchrzanowski.windsurfingforecast.service.response.ForecastResponse;

import java.time.LocalDate;

public interface ForecastService {

    ForecastResponse getForecast(SpotDTO spotDTO, LocalDate localDate);
}
