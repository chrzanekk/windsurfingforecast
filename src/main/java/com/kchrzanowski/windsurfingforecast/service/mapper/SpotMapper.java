package com.kchrzanowski.windsurfingforecast.service.mapper;

import com.kchrzanowski.windsurfingforecast.domain.Spot;
import com.kchrzanowski.windsurfingforecast.service.dto.SpotDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface SpotMapper extends EntityMapper<SpotDTO, Spot> {

    default Spot fromId(Long id) {
        if (id == null) {
            return null;
        }
        Spot spot = new Spot();
        spot.setId(id);
        return spot;
    }
}
