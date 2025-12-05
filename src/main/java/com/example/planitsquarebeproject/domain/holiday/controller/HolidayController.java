package com.example.planitsquarebeproject.domain.holiday.controller;

import com.example.planitsquarebeproject.domain.holiday.entity.Holiday;
import com.example.planitsquarebeproject.domain.holiday.repository.HolidayRepository;
import com.example.planitsquarebeproject.domain.holiday.service.HolidayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/holidays")
public class HolidayController {

    private final HolidayService holidayService;

    @GetMapping
    public List<Holiday> search(
            @RequestParam int year,
            @RequestParam String country) {
        return holidayService.search(year, country);
    }

    @GetMapping("/range")
    public List<Holiday> searchRange(
            @RequestParam LocalDate from,
            @RequestParam LocalDate to) {
        return holidayService.searchBetween(from, to);
    }

    @PostMapping("/refresh")
    public List<Holiday> refresh(
            @RequestParam int year,
            @RequestParam String country) {
        return holidayService.loadHolidays(year, country);
    }

    @DeleteMapping
    public void delete(
            @RequestParam int year,
            @RequestParam String country) {
        holidayService.delete(year, country);
    }
}
