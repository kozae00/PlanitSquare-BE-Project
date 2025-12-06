package com.example.planitsquarebeproject.domain.country.dto;

import com.example.planitsquarebeproject.domain.country.entity.Country;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CountryDto {

    // 외부 API 응답용 (기존)
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        @JsonProperty("countryCode")
        private String countryCode;

        private String name;

        public static Response from(Country country) {
            return Response.builder()
                    .countryCode(country.getCountryCode())
                    .name(country.getCountryName())
                    .build();
        }
    }
}
