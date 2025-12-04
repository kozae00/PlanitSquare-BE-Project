package com.example.planitsquarebeproject.domain.country.controller;

import com.example.planitsquarebeproject.domain.country.entity.Country;
import com.example.planitsquarebeproject.domain.country.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/countries")
public class CountryController {

    private final CountryService countryService;

    @PostMapping("/load")
    public List<Country> load() {
        return countryService.loadAllCountries();
    }

    @GetMapping
    public List<Country> findAll() {
        return countryService.getAll();
    }
}
