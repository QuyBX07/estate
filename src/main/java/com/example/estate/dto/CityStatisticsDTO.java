package com.example.estate.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CityStatisticsDTO {
    private String city;
    private long postcount;
    private double avengerPrice;
    private String popularType;
}
