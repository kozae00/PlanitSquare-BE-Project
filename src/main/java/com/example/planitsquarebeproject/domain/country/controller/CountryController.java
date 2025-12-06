package com.example.planitsquarebeproject.domain.country.controller;

import com.example.planitsquarebeproject.domain.country.entity.Country;
import com.example.planitsquarebeproject.domain.country.service.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Country", description = "국가 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/countries")
public class CountryController {

    private final CountryService countryService;

    @Operation(summary = "국가 데이터 로드", description = "외부 API에서 국가 데이터를 가져와 저장합니다.")
    @PostMapping("/load")
    public ResponseEntity<List<Country>> load() {
        List<Country> countries = countryService.loadAllCountries();
        return ResponseEntity.ok(countries);
    }

    @Operation(summary = "전체 국가 조회", description = "데이터베이스에 저장된 모든 국가를 조회합니다.")
    @GetMapping
    public ResponseEntity<List<Country>> findAll() {
        List<Country> countries = countryService.getAll();
        return ResponseEntity.ok(countries);
    }

    @Operation(summary = "국가 코드로 조회", description = "특정 국가 코드로 국가를 조회합니다.")
    @GetMapping("/{countryCode}")
    public ResponseEntity<Country> findByCode(@PathVariable String countryCode) {
        Country country = countryService.getByCode(countryCode);
        return ResponseEntity.ok(country);
    }
}
