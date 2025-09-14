package com.example.estate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyTypeSummaryDTO {
    private String type;
    private long totalListings;
    private double avgPrice;
    private double avgArea;
    private double marketShare;
    private String hotCity;
    private double minPrice;
    private double maxPrice;

    // field tạm để lấy từ aggregation
    private List<String> cities;
}
