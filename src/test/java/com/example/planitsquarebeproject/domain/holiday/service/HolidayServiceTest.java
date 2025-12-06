package com.example.planitsquarebeproject.domain.holiday.service;

import com.example.planitsquarebeproject.domain.holiday.dto.HolidayDto;
import com.example.planitsquarebeproject.domain.holiday.entity.Holiday;
import com.example.planitsquarebeproject.domain.holiday.exception.HolidayNotFoundException;
import com.example.planitsquarebeproject.domain.holiday.repository.HolidayRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HolidayServiceTest {

    @Mock
    private HolidayRepository holidayRepository;

    @InjectMocks
    private HolidayService holidayService;

    @Test
    @DisplayName("특정 연도와 국가의 공휴일을 조회할 수 있다")
    void search() {
        // given
        List<Holiday> holidays = List.of(
                createHoliday(1L, 2024, "KR", "New Year")
        );
        when(holidayRepository.findByYearAndCountryCode(2024, "KR"))
                .thenReturn(holidays);

        // when
        List<HolidayDto.Response> result = holidayService.search(2024, "KR");

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("New Year");
        assertThat(result.get(0).getYear()).isEqualTo(2024);
    }

    @Test
    @DisplayName("공휴일이 없으면 예외가 발생한다")
    void searchNotFound() {
        // given
        when(holidayRepository.findByYearAndCountryCode(2024, "XX"))
                .thenReturn(List.of());

        // when & then
        assertThatThrownBy(() -> holidayService.search(2024, "XX"))
                .isInstanceOf(HolidayNotFoundException.class)
                .hasMessageContaining("2024")
                .hasMessageContaining("XX");
    }

    @Test
    @DisplayName("잘못된 연도로 조회하면 예외가 발생한다")
    void searchWithInvalidYear() {
        // when & then
        assertThatThrownBy(() -> holidayService.search(3000, "KR"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("유효하지 않은 연도");
    }

    @Test
    @DisplayName("날짜 범위로 공휴일을 조회할 수 있다")
    void searchBetween() {
        // given
        List<Holiday> holidays = List.of(
                createHoliday(1L, 2024, "KR", "Holiday1"),
                createHoliday(2L, 2024, "KR", "Holiday2")
        );
        LocalDate from = LocalDate.of(2024, 1, 1);
        LocalDate to = LocalDate.of(2024, 12, 31);
        
        when(holidayRepository.findByDateBetween(from, to))
                .thenReturn(holidays);

        // when
        List<HolidayDto.Response> result = holidayService.searchBetween(from, to);

        // then
        assertThat(result).hasSize(2);
    }

    @Test
    @DisplayName("시작 날짜가 종료 날짜보다 늦으면 예외가 발생한다")
    void searchBetweenInvalidRange() {
        // given
        LocalDate from = LocalDate.of(2024, 12, 31);
        LocalDate to = LocalDate.of(2024, 1, 1);

        // when & then
        assertThatThrownBy(() -> holidayService.searchBetween(from, to))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("시작 날짜가 종료 날짜보다 늦을 수 없습니다");
    }

    @Test
    @DisplayName("공휴일을 삭제할 수 있다")
    void delete() {
        // when
        holidayService.delete(2024, "KR");

        // then
        verify(holidayRepository, times(1))
                .deleteAllByYearAndCountryCode(2024, "KR");
    }

    private Holiday createHoliday(Long id, int year, String countryCode, String name) {
        return Holiday.builder()
                .id(id)
                .year(year)
                .countryCode(countryCode)
                .date(LocalDate.of(year, 1, 1))
                .name(name)
                .localName("로컬명")
                .type("Public")
                .build();
    }
}
