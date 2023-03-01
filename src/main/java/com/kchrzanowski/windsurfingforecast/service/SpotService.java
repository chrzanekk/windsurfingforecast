package com.kchrzanowski.windsurfingforecast.service;

import com.kchrzanowski.windsurfingforecast.service.dto.SpotDTO;

import java.util.List;

public interface SpotService {

    List<SpotDTO> getAllSpots();

    SpotDTO getSpotById(Long id);

}
