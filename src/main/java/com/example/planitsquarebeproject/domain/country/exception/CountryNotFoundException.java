package com.example.planitsquarebeproject.domain.country.exception;

import com.example.planitsquarebeproject.global.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class CountryNotFoundException extends BusinessException {
    
    public CountryNotFoundException(String countryCode) {
        super("국가를 찾을 수 없습니다: " + countryCode, HttpStatus.NOT_FOUND);
    }
}
