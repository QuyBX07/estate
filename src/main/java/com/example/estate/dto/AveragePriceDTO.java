package com.example.estate.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AveragePriceDTO {
    private String city;
    private int year;
    private int month;
    private double avgPrice;
    private long count;
}
