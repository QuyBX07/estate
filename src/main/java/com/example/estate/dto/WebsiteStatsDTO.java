package com.example.estate.dto;

import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WebsiteStatsDTO {
    private String website;
    private long postcount;
    private double averagePrice;
    private double percent;
}
