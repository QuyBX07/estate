package com.example.estate.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PriceAllocationDTO {
    private long price;
    private double percent;

}
