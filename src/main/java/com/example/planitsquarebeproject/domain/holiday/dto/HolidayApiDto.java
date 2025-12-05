package com.example.planitsquarebeproject.domain.holiday.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

public class HolidayApiDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {

        // "2025-01-01" 같은 String
        private String date;

        private String localName;
        private String name;

        @JsonProperty("countryCode")
        private String countryCode;

        // ["Public", "Bank"] 형태
        private List<String> types;
    }
}

