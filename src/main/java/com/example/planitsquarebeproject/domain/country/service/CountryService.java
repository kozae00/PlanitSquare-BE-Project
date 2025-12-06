package com.example.planitsquarebeproject.domain.country.service;

import com.example.planitsquarebeproject.domain.country.dto.CountryDto;
import com.example.planitsquarebeproject.domain.country.entity.Country;
import com.example.planitsquarebeproject.domain.country.exception.CountryNotFoundException;
import com.example.planitsquarebeproject.domain.country.repository.CountryRepository;
import com.example.planitsquarebeproject.global.infrastructure.NagerApiClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CountryService {

    private final NagerApiClient nagerApiClient;
    private final CountryRepository countryRepository;

    @Transactional
    public List<Country> loadAllCountries() {
        log.info("외부 API에서 국가 데이터 가져오는 중...");
        
        try {
            List<CountryDto.Response> apiCountries = nagerApiClient.getAvailableCountries();
            
            if (apiCountries == null || apiCountries.isEmpty()) {
                throw new IllegalStateException("외부 API로부터 국가 데이터를 가져올 수 없습니다.");
            }
            
            log.info("외부 API에서 {} 개 국가 데이터 수신", apiCountries.size());

            List<Country> entities = apiCountries.stream()
                    .map(dto -> new Country(dto.getCountryCode(), dto.getName()))
                    .toList();

            countryRepository.saveAll(entities);
            log.info("데이터베이스에 {} 개 국가 저장 완료", entities.size());
            
            return entities;
        } catch (Exception e) {
            log.error("국가 데이터 로드 실패", e);
            throw e;
        }
    }

    public List<Country> getAll() {
        List<Country> countries = countryRepository.findAll();
        
        if (countries.isEmpty()) {
            log.warn("데이터베이스에 국가 데이터가 없습니다.");
        }
        
        return countries;
    }
    
    public Country getByCode(String countryCode) {
        return countryRepository.findById(countryCode)
                .orElseThrow(() -> new CountryNotFoundException(countryCode));
    }
}
