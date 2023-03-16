package com.kchrzanowski.windsurfingforecast.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kchrzanowski.windsurfingforecast.controller.SpotController;
import com.kchrzanowski.windsurfingforecast.service.dto.ForecastDTO;
import com.kchrzanowski.windsurfingforecast.service.response.SpotResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SpotController.class)
public class SpotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpotService spotService;

    private static String DATE = "2023-03-19";
    private static String API = "/api/forecast/spot";

    @Test
    public void shouldReturnExpectedSpotForDate() throws Exception {

        SpotResponse expectedSpotResponse = SpotResponse.builder()
                .spotName("Jastarnia")
                .forecastDTO(ForecastDTO.builder()
                        .windSpeed(3f)
                        .averageTemperature(2f)
                        .spotScoring((3f * 3f) + 2f)
                        .dateTime(getLocalDate())
                        .build()).build();
        given(spotService.getBestSpot(getLocalDate())).willReturn(expectedSpotResponse);

        MvcResult result = mockMvc.perform(get(API)
                        .param("date", DATE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        SpotResponse receivedSpotResult = mapper.readValue(result.getResponse().getContentAsString(),
                SpotResponse.class);
        assertThat(receivedSpotResult).isEqualTo(expectedSpotResponse);
    }

    @Test
    public void ShouldReturnEmptyResponseWhenDontFindSpot() throws Exception {
        SpotResponse expectedSpotResponse = SpotResponse.builder().build();

        given(spotService.getBestSpot(getLocalDate())).willReturn(expectedSpotResponse);

        MvcResult result = mockMvc.perform(get(API)
                        .param("date", DATE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        SpotResponse receivedSpotResult = mapper.readValue(result.getResponse().getContentAsString(),
                SpotResponse.class);
        assertThat(receivedSpotResult).isEqualTo(expectedSpotResponse);

    }



    private LocalDate getLocalDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(DATE, formatter);
    }
}
