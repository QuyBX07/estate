package com.example.estate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyTypeTrendDTO {
    private String type;   
    private long count;    
    private double avgPrice;
    private double avgArea;
}
