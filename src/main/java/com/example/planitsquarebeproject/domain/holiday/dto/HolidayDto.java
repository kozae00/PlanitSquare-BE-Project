package com.example.planitsquarebeproject.domain.holiday.dto;

import com.example.planitsquarebeproject.domain.holiday.entity.Holiday;
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

        public static Response from(Holiday holiday) {
            return Response.builder()
                    .id(holiday.getId())
                    .year(holiday.getYear())
                    .countryCode(holiday.getCountryCode())
                    .date(holiday.getDate())
                    .name(holiday.getName())
                    .localName(holiday.getLocalName())
                    .type(holiday.getType())
                    .build();
        }
    }
}
