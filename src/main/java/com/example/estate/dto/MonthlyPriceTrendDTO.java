package com.example.estate.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthlyPriceTrendDTO {
    private int year;
    private int month;
    private double averagePrice;
    private int postcount;
}
