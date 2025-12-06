package com.example.planitsquarebeproject.domain.holiday.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class HolidayDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long id;
        private int year;
        private String countryCode;
        private LocalDate date;
        private String name;
        private String localName;
        private String type;
    }
}
