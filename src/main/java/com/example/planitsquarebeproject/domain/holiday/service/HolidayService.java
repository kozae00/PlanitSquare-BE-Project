package com.example.planitsquarebeproject.domain.holiday.service;

import com.example.planitsquarebeproject.domain.country.entity.Country;
import com.example.planitsquarebeproject.domain.holiday.dto.HolidayApiDto;
import com.example.planitsquarebeproject.domain.holiday.dto.HolidayDto;
import com.example.planitsquarebeproject.domain.holiday.entity.Holiday;
import com.example.planitsquarebeproject.domain.holiday.repository.HolidayRepository;
import com.example.planitsquarebeproject.global.infrastructure.NagerApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HolidayService {

    private final HolidayRepository holidayRepository;
    private final NagerApiClient nagerApiClient;

    @Transactional
    public List<HolidayDto.Response> loadHolidays(int year, String countryCode) {
        validateYearAndCountryCode(year, countryCode);
        
        try {
            List<HolidayApiDto.Response> apiHolidays = nagerApiClient.getPublicHolidays(year, countryCode);

            if (apiHolidays == null || apiHolidays.isEmpty()) {
                log.warn("{}년 {}의 공휴일 데이터가 없습니다.", year, countryCode);
                return List.of();
            }

            List<Holiday> entities = apiHolidays.stream()
                    .map(dto -> Holiday.builder()
                            .year(year)
                            .countryCode(countryCode)
                            .date(LocalDate.parse(dto.getDate()))
                            .name(dto.getName())
                            .localName(dto.getLocalName())
                            .type(dto.getTypes().get(0))
                            .build())
                    .toList();

            holidayRepository.deleteAllByYearAndCountryCode(year, countryCode);
            List<Holiday> savedHolidays = holidayRepository.saveAll(entities);
            
            return savedHolidays.stream()
                    .map(HolidayDto.Response::from)
                    .toList();
        } catch (Exception e) {
            log.error("공휴일 데이터 로드 실패: {}년 {}", year, countryCode, e);
            throw e;
        }
    }

    @Transactional
    public void loadInitialData(List<Country> countries) {
        log.info("공휴일 초기 데이터 로드 시작: {} 개 국가, 2020-2025년", countries.size());
        
        int totalLoaded = 0;
        for (Country country : countries) {
            for (int year = 2020; year <= 2025; year++) {
                try {
                    List<HolidayDto.Response> holidays = loadHolidays(year, country.getCountryCode());
                    totalLoaded += holidays.size();
                    log.debug("{} {}년 공휴일 로드 완료: {} 개", 
                             country.getCountryCode(), year, holidays.size());
                } catch (Exception e) {
                    log.warn("{} {}년 공휴일 로드 실패: {}", 
                            country.getCountryCode(), year, e.getMessage());
                }
            }
        }
        
        log.info("공휴일 초기 데이터 로드 완료: 총 {} 개", totalLoaded);
    }

    public List<HolidayDto.Response> search(int year, String countryCode) {
        validateYearAndCountryCode(year, countryCode);
        
        List<Holiday> holidays = holidayRepository.findByYearAndCountryCode(year, countryCode);

        return holidays.stream()
                .map(HolidayDto.Response::from)
                .toList();
    }

    public List<HolidayDto.Response> searchBetween(LocalDate from, LocalDate to) {
        if (from.isAfter(to)) {
            throw new IllegalArgumentException("시작 날짜가 종료 날짜보다 늦을 수 없습니다.");
        }
        
        List<Holiday> holidays = holidayRepository.findByDateBetween(from, to);
        
        return holidays.stream()
                .map(HolidayDto.Response::from)
                .toList();
    }

    @Transactional
    public void delete(int year, String countryCode) {
        validateYearAndCountryCode(year, countryCode);
        holidayRepository.deleteAllByYearAndCountryCode(year, countryCode);
    }
    
    private void validateYearAndCountryCode(int year, String countryCode) {
        if (year < 1900 || year > 2100) {
            throw new IllegalArgumentException("유효하지 않은 연도입니다: " + year);
        }
        
        if (countryCode == null || countryCode.trim().isEmpty()) {
            throw new IllegalArgumentException("국가 코드는 필수입니다.");
        }
    }
}
