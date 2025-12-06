package com.example.planitsquarebeproject.domain.holiday.controller;

import com.example.planitsquarebeproject.domain.holiday.dto.HolidayDto;
import com.example.planitsquarebeproject.domain.holiday.service.HolidayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Holiday", description = "공휴일 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/holidays")
public class HolidayController {

    private final HolidayService holidayService;

    @Operation(summary = "공휴일 조회", description = "특정 연도와 국가의 공휴일을 조회합니다.")
    @GetMapping
    public ResponseEntity<List<HolidayDto.Response>> search(
            @RequestParam int year,
            @RequestParam String country) {
        List<HolidayDto.Response> holidays = holidayService.search(year, country);
        return ResponseEntity.ok(holidays);
    }

    @Operation(summary = "기간별 공휴일 조회", description = "특정 기간의 공휴일을 조회합니다.")
    @GetMapping("/range")
    public ResponseEntity<List<HolidayDto.Response>> searchRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        List<HolidayDto.Response> holidays = holidayService.searchBetween(from, to);
        return ResponseEntity.ok(holidays);
    }

    @Operation(summary = "공휴일 새로고침", description = "외부 API에서 공휴일 데이터를 다시 가져와 업데이트합니다.")
    @PostMapping("/refresh")
    public ResponseEntity<List<HolidayDto.Response>> refresh(
            @RequestParam int year,
            @RequestParam String country) {
        List<HolidayDto.Response> holidays = holidayService.loadHolidays(year, country);
        return ResponseEntity.ok(holidays);
    }

    @Operation(summary = "공휴일 삭제", description = "특정 연도와 국가의 공휴일 데이터를 삭제합니다.")
    @DeleteMapping
    public ResponseEntity<Void> delete(
            @RequestParam int year,
            @RequestParam String country) {
        holidayService.delete(year, country);
        return ResponseEntity.noContent().build();
    }
}
