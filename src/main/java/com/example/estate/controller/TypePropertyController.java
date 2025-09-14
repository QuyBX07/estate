package com.example.estate.controller;

import com.example.estate.dto.PropertyTypeSummaryDTO;
import com.example.estate.dto.PropertyTypeTrendDTO;
import com.example.estate.service.PropertyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/properties") // 👈 base path
public class TypePropertyController {

    private final PropertyService propertyService;

    public TypePropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping("/summary")
    public List<PropertyTypeSummaryDTO> getSummaryByType() {
        return propertyService.getPropertyTypeSummary();
    }

    @GetMapping("/type-trend")
    public List<PropertyTypeTrendDTO> getTypeTrendLast7Days() {
        return propertyService.getPropertyTypeTrendLast7Days();
    }
}
