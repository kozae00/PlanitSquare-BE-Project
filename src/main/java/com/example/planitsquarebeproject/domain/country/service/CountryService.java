package com.example.planitsquarebeproject.domain.country.service;

import com.example.planitsquarebeproject.domain.country.dto.CountryDto;
import com.example.planitsquarebeproject.domain.country.entity.Country;
import com.example.planitsquarebeproject.domain.country.repository.CountryRepository;
import com.example.planitsquarebeproject.global.infrastructure.NagerApiClient;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final NagerApiClient nagerApiClient;
    private final CountryRepository countryRepository;

    @Transactional
    public List<Country> loadAllCountries() {
        List<CountryDto.Response> apiCountries = nagerApiClient.getAvailableCountries();

        List<Country> entities = apiCountries.stream()
                .map(dto -> new Country(dto.getCountryCode(), dto.getName()))
                .toList();

        countryRepository.saveAll(entities);
        return entities;
    }

    public List<Country> getAll() {
        return countryRepository.findAll();
    }
}
