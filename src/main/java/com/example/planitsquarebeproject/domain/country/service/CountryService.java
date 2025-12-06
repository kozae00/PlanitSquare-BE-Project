package com.example.planitsquarebeproject.domain.country.service;

import com.example.planitsquarebeproject.domain.country.dto.CountryDto;
import com.example.planitsquarebeproject.domain.country.entity.Country;
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
        List<CountryDto.Response> apiCountries = nagerApiClient.getAvailableCountries();
        log.info("외부 API에서 {} 개 국가 데이터 수신", apiCountries.size());

        List<Country> entities = apiCountries.stream()
                .map(dto -> new Country(dto.getCountryCode(), dto.getName()))
                .toList();

        countryRepository.saveAll(entities);
        log.info("데이터베이스에 {} 개 국가 저장 완료", entities.size());
        
        return entities;
    }

    public List<Country> getAll() {
        return countryRepository.findAll();
    }
}
