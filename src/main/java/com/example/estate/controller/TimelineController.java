package com.example.estate.controller;

import com.example.estate.dto.PriceStatDTO;
import com.example.estate.dto.TimeStatDTO;
import com.example.estate.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timeline")
@RequiredArgsConstructor
public class TimelineController {

    private final PropertyService propertyService;

    @GetMapping("/last7days")
    public List<TimeStatDTO> getLast7DaysStats() {
        return propertyService.getPostStatsLast7Days();
    }

    @GetMapping("/weeks")
    public List<TimeStatDTO> getStatsByWeeks(
            @RequestParam int year,
            @RequestParam int month) {
        return propertyService.getPostStatsByWeeks(year, month);
    }

    @GetMapping("/year")
    public List<TimeStatDTO> getStatsByYear(@RequestParam int year) {
        return propertyService.getPostStatsByYear(year);
    }

    @GetMapping("/price/last7days")
    public List<PriceStatDTO> getAvgPriceLast7Days() {
        return propertyService.getAvgPriceLast7Days();
    }

    // Theo tuần trong tháng
    @GetMapping("/price/weeks")
    public List<PriceStatDTO> getAvgPriceByWeeks(
            @RequestParam int year,
            @RequestParam int month) {
        return propertyService.getAvgPriceByWeeks(year, month);
    }

    // Theo năm (12 tháng)
    @GetMapping("/price/year")
    public List<PriceStatDTO> getAvgPriceByYear(@RequestParam int year) {
        return propertyService.getAvgPriceByYear(year);
    }
}
