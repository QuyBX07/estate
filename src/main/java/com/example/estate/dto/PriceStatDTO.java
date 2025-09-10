package com.example.estate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceStatDTO {
    private String label;    // "02/09", "Tuần 2", "Tháng 8"
    private double avgPrice; // giá trung bình
}
