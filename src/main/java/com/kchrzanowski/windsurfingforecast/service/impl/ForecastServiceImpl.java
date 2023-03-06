package com.kchrzanowski.windsurfingforecast.service.impl;

import com.kchrzanowski.windsurfingforecast.service.ForecastService;
import com.kchrzanowski.windsurfingforecast.service.dto.ForecastDTO;
import com.kchrzanowski.windsurfingforecast.service.dto.SpotDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ForecastServiceImpl implements ForecastService {

    private final Logger log = LoggerFactory.getLogger(ForecastServiceImpl.class);

    @Override
    public ForecastDTO getForecast(SpotDTO spotDTO, LocalDate localDate) {
        return null;
    }
}
