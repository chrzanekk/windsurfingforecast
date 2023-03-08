package com.kchrzanowski.windsurfingforecast.service;

import com.kchrzanowski.windsurfingforecast.service.dto.SpotDTO;
import com.kchrzanowski.windsurfingforecast.service.response.SpotResponse;

import java.time.LocalDate;
import java.util.List;

public interface SpotService {

    SpotDTO addNew(SpotDTO spotDTO);
    SpotDTO update(SpotDTO spotDTO);
    List<SpotDTO> getAllSpots();
    SpotDTO getSpotById(Long id);
    void delete(Long id);
    SpotResponse getBestSpots(LocalDate date);

}
