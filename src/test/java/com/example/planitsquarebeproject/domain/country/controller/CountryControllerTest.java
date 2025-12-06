package com.example.planitsquarebeproject.domain.country.controller;

import com.example.planitsquarebeproject.domain.country.dto.CountryDto;
import com.example.planitsquarebeproject.domain.country.service.CountryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CountryControllerTest {

    @Mock
    private CountryService countryService;

    @InjectMocks
    private CountryController countryController;

    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /api/v1/countries - 모든 국가 조회 API 테스트")
    void findAll() throws Exception {
        // given
        mockMvc = MockMvcBuilders.standaloneSetup(countryController).build();
        
        List<CountryDto.Response> countries = List.of(
                CountryDto.Response.builder()
                        .countryCode("KR")
                        .name("South Korea")
                        .build(),
                CountryDto.Response.builder()
                        .countryCode("US")
                        .name("United States")
                        .build()
        );
        when(countryService.getAll()).thenReturn(countries);

        // when & then
        mockMvc.perform(get("/api/v1/countries"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].countryCode").value("KR"))
                .andExpect(jsonPath("$[1].countryCode").value("US"));
    }

    @Test
    @DisplayName("GET /api/v1/countries/{countryCode} - 특정 국가 조회 API 테스트")
    void findByCode() throws Exception {
        // given
        mockMvc = MockMvcBuilders.standaloneSetup(countryController).build();
        
        CountryDto.Response country = CountryDto.Response.builder()
                .countryCode("KR")
                .name("South Korea")
                .build();
        when(countryService.getByCode("KR")).thenReturn(country);

        // when & then
        mockMvc.perform(get("/api/v1/countries/KR"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.countryCode").value("KR"))
                .andExpect(jsonPath("$.name").value("South Korea"));
    }
}
