package com.kchrzanowski.windsurfingforecast.service;

import com.kchrzanowski.windsurfingforecast.service.dto.ForecastDTO;
import com.kchrzanowski.windsurfingforecast.service.dto.SpotDTO;

import java.time.LocalDate;

public interface ForecastService {

    ForecastDTO getForecast(SpotDTO spotDTO, LocalDate localDate);
}
