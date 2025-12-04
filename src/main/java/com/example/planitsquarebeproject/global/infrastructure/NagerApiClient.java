package com.example.planitsquarebeproject.global.infrastructure;

import com.example.planitsquarebeproject.domain.country.dto.CountryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NagerApiClient {

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://date.nager.at/api/v3")
            .build();

    public List<CountryDto.Response> getAvailableCountries() {
        return webClient.get()
                .uri("/AvailableCountries")
                .retrieve()
                .bodyToFlux(CountryDto.Response.class)
                .collectList()
                .block();
    }
}
