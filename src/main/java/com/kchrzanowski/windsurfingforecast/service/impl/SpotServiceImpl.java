package com.kchrzanowski.windsurfingforecast.service.impl;

import com.kchrzanowski.windsurfingforecast.domain.Spot;
import com.kchrzanowski.windsurfingforecast.exception.SpotNotFoundException;
import com.kchrzanowski.windsurfingforecast.repository.SpotRepository;
import com.kchrzanowski.windsurfingforecast.service.SpotService;
import com.kchrzanowski.windsurfingforecast.service.dto.SpotDTO;
import com.kchrzanowski.windsurfingforecast.service.mapper.SpotMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpotServiceImpl implements SpotService {

    private static final String SPOT_NOT_FOUND = "Spot not found";

    private final Logger log = LoggerFactory.getLogger(SpotServiceImpl.class);

    private final SpotRepository spotRepository;
    private final SpotMapper spotMapper;

    public SpotServiceImpl(SpotRepository spotRepository, SpotMapper spotMapper) {
        this.spotRepository = spotRepository;
        this.spotMapper = spotMapper;
    }

    @Override
    public List<SpotDTO> getAllSpots() {
        log.debug("Get all spots.");
        return spotMapper.toDto(spotRepository.findAll());
    }

    @Override
    public SpotDTO getSpotById(Long id) {
        log.debug("Find spot by id: {}", id);
        Optional<Spot> optionalSpot = spotRepository.findById(id);
        return spotMapper.toDto(optionalSpot.orElseThrow(() -> new SpotNotFoundException(SPOT_NOT_FOUND + ": " + id)));
    }
}
