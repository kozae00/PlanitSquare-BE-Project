package com.example.planitsquarebeproject.domain.holiday.service;

import com.example.planitsquarebeproject.domain.country.entity.Country;
import com.example.planitsquarebeproject.domain.holiday.dto.HolidayApiDto;
import com.example.planitsquarebeproject.domain.holiday.entity.Holiday;
import com.example.planitsquarebeproject.domain.holiday.repository.HolidayRepository;
import com.example.planitsquarebeproject.global.infrastructure.NagerApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class HolidayService {

    private final HolidayRepository holidayRepository;
    private final NagerApiClient nagerApiClient;

    @Transactional
    public List<Holiday> loadHolidays(int year, String countryCode) {

        List<HolidayApiDto.Response> apiHolidays = nagerApiClient.getPublicHolidays(year, countryCode);


        List<Holiday> entities = apiHolidays .stream()
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
        return holidayRepository.saveAll(entities);
    }

    @Transactional
    public void loadInitialData(List<Country> countries) {
        log.info("공휴일 초기 데이터 로드 시작: {} 개 국가, 2020-2025년", countries.size());
        
        int totalLoaded = 0;
        for (Country country : countries) {
            for (int year = 2020; year <= 2025; year++) {
                try {
                    List<Holiday> holidays = loadHolidays(year, country.getCountryCode());
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

    public List<Holiday> search(int year, String countryCode) {
        return holidayRepository.findByYearAndCountryCode(year, countryCode);
    }

    public List<Holiday> searchBetween(LocalDate from, LocalDate to) {
        return holidayRepository.findByDateBetween(from, to);
    }

    public void delete(int year, String countryCode) {
        holidayRepository.deleteAllByYearAndCountryCode(year, countryCode);
    }
}
