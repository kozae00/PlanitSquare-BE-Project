package com.example.planitsquarebeproject.domain.holiday.controller;

import com.example.planitsquarebeproject.domain.holiday.dto.HolidayDto;
import com.example.planitsquarebeproject.domain.holiday.service.HolidayService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class HolidayControllerTest {

    @Mock
    private HolidayService holidayService;

    @InjectMocks
    private HolidayController holidayController;

    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /api/v1/holidays - 공휴일 조회 API 테스트")
    void search() throws Exception {
        // given
        mockMvc = MockMvcBuilders.standaloneSetup(holidayController).build();
        
        List<HolidayDto.Response> holidays = List.of(
                HolidayDto.Response.builder()
                        .id(1L)
                        .year(2024)
                        .countryCode("KR")
                        .date(LocalDate.of(2024, 1, 1))
                        .name("New Year")
                        .localName("새해")
                        .type("Public")
                        .build()
        );
        when(holidayService.search(2024, "KR")).thenReturn(holidays);

        // when & then
        mockMvc.perform(get("/api/v1/holidays")
                        .param("year", "2024")
                        .param("country", "KR"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].year").value(2024))
                .andExpect(jsonPath("$[0].countryCode").value("KR"))
                .andExpect(jsonPath("$[0].name").value("New Year"));
    }

    @Test
    @DisplayName("GET /api/v1/holidays/range - 기간별 공휴일 조회 API 테스트")
    void searchRange() throws Exception {
        // given
        mockMvc = MockMvcBuilders.standaloneSetup(holidayController).build();
        
        List<HolidayDto.Response> holidays = List.of(
                HolidayDto.Response.builder()
                        .id(1L)
                        .year(2024)
                        .countryCode("KR")
                        .date(LocalDate.of(2024, 3, 1))
                        .name("Holiday")
                        .localName("공휴일")
                        .type("Public")
                        .build()
        );
        LocalDate from = LocalDate.of(2024, 1, 1);
        LocalDate to = LocalDate.of(2024, 12, 31);
        when(holidayService.searchBetween(from, to)).thenReturn(holidays);

        // when & then
        mockMvc.perform(get("/api/v1/holidays/range")
                        .param("from", "2024-01-01")
                        .param("to", "2024-12-31"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @DisplayName("DELETE /api/v1/holidays - 공휴일 삭제 API 테스트")
    void deleteHoliday() throws Exception {
        // given
        mockMvc = MockMvcBuilders.standaloneSetup(holidayController).build();
        
        // when & then
        mockMvc.perform(delete("/api/v1/holidays")
                        .param("year", "2024")
                        .param("country", "KR"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(holidayService).delete(2024, "KR");
    }
}
