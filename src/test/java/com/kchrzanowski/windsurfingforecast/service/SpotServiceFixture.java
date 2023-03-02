package com.kchrzanowski.windsurfingforecast.service;

import com.kchrzanowski.windsurfingforecast.service.dto.SpotDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;


@Service
public class SpotServiceFixture {
    @Autowired
    private SpotService spotService;
    @Autowired
    private EntityManager em;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createSpot() {

        SpotDTO firstSpotDTO = SpotDTO.builder().name("Jastarnia").latitude(54.6960600).longitude(18.6787300).build();
        spotService.addNew(firstSpotDTO);
        SpotDTO secondSpotDTO = SpotDTO.builder().name("Bridgetown").latitude(13.1000000).longitude(-59.6166700).build();
        spotService.addNew(secondSpotDTO);
        SpotDTO thirdSpotDTO = SpotDTO.builder().name("Fortaleza").latitude(-3.7172200).longitude(-38.5430600).build();
        spotService.addNew(thirdSpotDTO);
        SpotDTO fourthSpotDTO = SpotDTO.builder().name("Pissouri").latitude(34.6694200).longitude(32.7013200).build();
        spotService.addNew(fourthSpotDTO);
        SpotDTO fifthSpotDTO = SpotDTO.builder().name("Le Morne").latitude(57.3284609).longitude(-20.4450478).build();
        spotService.addNew(fifthSpotDTO);

        em.flush();
    }

}
