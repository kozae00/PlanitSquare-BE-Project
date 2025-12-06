package com.example.planitsquarebeproject.global.init;

import com.example.planitsquarebeproject.domain.country.entity.Country;
import com.example.planitsquarebeproject.domain.country.service.CountryService;
import com.example.planitsquarebeproject.domain.holiday.service.HolidayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BaseInitData implements CommandLineRunner {

    private final CountryService countryService;
    private final HolidayService holidayService;

    @Override
    public void run(String... args) throws Exception {
        log.info("=== 데이터 초기화 시작 ===");
        
        try {
            // 1. 국가 데이터 로드
            log.info("국가 데이터 로딩 중...");
            List<Country> countries = countryService.loadAllCountries();
            log.info("국가 데이터 로딩 완료: {} 개국", countries.size());
            
            // 2. 공휴일 데이터 로드
            log.info("공휴일 데이터 로딩 중...");
            holidayService.loadInitialData(countries);
            log.info("공휴일 데이터 로딩 완료");
            
            log.info("=== 데이터 초기화 완료 ===");
        } catch (Exception e) {
            log.error("데이터 초기화 실패", e);
            throw e;
        }
    }
}
