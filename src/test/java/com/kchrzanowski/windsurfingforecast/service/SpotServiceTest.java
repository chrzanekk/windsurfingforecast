package com.kchrzanowski.windsurfingforecast.service;

import com.kchrzanowski.windsurfingforecast.WindsurfingforecastApplication;
import com.kchrzanowski.windsurfingforecast.repository.SpotRepository;
import com.kchrzanowski.windsurfingforecast.service.dto.SpotDTO;
import com.kchrzanowski.windsurfingforecast.service.impl.ForecastServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = WindsurfingforecastApplication.class)
public class SpotServiceTest {

    @Autowired
    private SpotService spotService;
    @Autowired
    private SpotServiceFixture spotServiceFixture;
    @Autowired
    private SpotRepository spotRepository;

    @Mock
    private ForecastServiceImpl forecastServiceImpl;
    @Mock
    private RestTemplate restTemplate;



    @BeforeEach
    public void init() {
        spotRepository.deleteAll();
        spotServiceFixture.createSpot();
        forecastServiceImpl = new ForecastServiceImpl(restTemplate);
    }

    @Test
    public void checkIfAnyDataExists() {

        int size = spotService.getAllSpots().size();

        assertThat(size).isNotZero();
    }

    @Test
    public void checkIfNewSpotWasAddedProperly() {

        List<SpotDTO> allSpotsBeforeTest = spotService.getAllSpots();
        int sizeBeforeTest = allSpotsBeforeTest.size();

        SpotDTO newSpot = SpotDTO.builder().name("New Spot").latitude(13.13).longitude(14.14).build();
        spotService.addNew(newSpot);

        List<SpotDTO> allSpotsAfterTest = spotService.getAllSpots();
        int sizeAfterTest = allSpotsAfterTest.size();

        assertThat(sizeAfterTest).isEqualTo(sizeBeforeTest + 1);
        assertThat(allSpotsAfterTest.get(sizeAfterTest - 1).getName()).isEqualTo(newSpot.getName());
        assertThat(allSpotsAfterTest.get(sizeAfterTest - 1).getLatitude()).isEqualTo(newSpot.getLatitude());
        assertThat(allSpotsAfterTest.get(sizeAfterTest - 1).getLongitude()).isEqualTo(newSpot.getLongitude());
    }

    @Test
    public void checkIfFindByIdReturnsCorrectData() {
        List<SpotDTO> allSpotsBeforeTest = spotService.getAllSpots();
        int sizeBeforeTest = allSpotsBeforeTest.size();

        SpotDTO spotDTO = spotService.getSpotById((long) sizeBeforeTest - 1);

        assertThat(spotDTO).isNotNull();
    }

    @Test
    public void checkIfUpdateSpotWasCorrected() {
        List<SpotDTO> allSpotsBeforeTest = spotService.getAllSpots();
        int sizeBeforeTest = allSpotsBeforeTest.size();
        SpotDTO spotToUpdate = allSpotsBeforeTest.get(0);

        String newSpotName = "Nowa Jastarnia";
        SpotDTO updatedSpot = SpotDTO.builder()
                .id(spotToUpdate.getId())
                .name(newSpotName)
                .latitude(spotToUpdate.getLatitude())
                .longitude(spotToUpdate.getLongitude()).build();

        spotService.update(updatedSpot);

        List<SpotDTO> allSpotsAfterTest = spotService.getAllSpots();
        int sizeAfterTest = allSpotsAfterTest.size();

        assertThat(sizeAfterTest).isEqualTo(sizeBeforeTest);
        assertThat(allSpotsAfterTest.get(0).getName()).isEqualTo(newSpotName);
    }

    @Test
    @Transactional
    public void checkIfDeleteIsWorking() {
        List<SpotDTO> allSpotsBeforeTest = spotService.getAllSpots();
        int sizeBeforeTest = allSpotsBeforeTest.size();

        spotService.delete((long) sizeBeforeTest - 1);

        List<SpotDTO> allSpotsAfterTest = spotService.getAllSpots();
        int sizeAfterTest = allSpotsAfterTest.size();

        assertThat(sizeBeforeTest - 1).isEqualTo(sizeAfterTest);
    }


}
