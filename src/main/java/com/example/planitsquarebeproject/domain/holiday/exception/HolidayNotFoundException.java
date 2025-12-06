package com.example.planitsquarebeproject.domain.holiday.exception;

import com.example.planitsquarebeproject.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class HolidayNotFoundException extends BusinessException {
    
    public HolidayNotFoundException(int year, String countryCode) {
        super(String.format("공휴일 정보를 찾을 수 없습니다: %d년 %s", year, countryCode), 
              HttpStatus.NOT_FOUND);
    }
}
