package com.example.planitsquarebeproject.domain.holiday.repository;

import com.example.planitsquarebeproject.domain.holiday.entity.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface HolidayRepository extends JpaRepository<Holiday, Long> {
    void deleteAllByYearAndCountryCode(int year, String countryCode);

    List<Holiday> findByYearAndCountryCode(int year, String countryCode);

    List<Holiday> findByDateBetween(LocalDate from, LocalDate to);
}
