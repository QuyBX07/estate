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
    private String numberPhone;
    private Long price;
    private Double area;
    private Double unit_price;
    private LocalDateTime postedDate;
    private String link;
    private String legal;
    private Float frontage;     // float → Float
    private String type;
    private Integer bedroom;    // int → Integer
    private Integer bathroom;   // int → Integer
    private String amenityLocation;
    private String source;
}
