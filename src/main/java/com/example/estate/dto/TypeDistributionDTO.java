package com.example.estate.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TypeDistributionDTO {
    private String type;
    private int postcount;
    private double percent;
}
