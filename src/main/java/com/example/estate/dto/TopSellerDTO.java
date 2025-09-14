package com.example.estate.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopSellerDTO {
    private String seller;
    private String phone;
    private long postCount;
    private long totalPrice;
}
