package com.example.estate.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyDTO {
    private String id;
    private String title;
    private String address;
    private String city;
    private String seller;
    private String phone;
    private Long price;
    private Double area;
    private Double unitPrice;
    private LocalDateTime postedDate;
    private String link;
    private Boolean legalStatus;
    private Integer facade;
    private String type;
}
