package com.example.estate.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CityStatisticsDTO {
    private String city;
    private long postcount;
    private double averagePrice;
    private String popularType;
}
