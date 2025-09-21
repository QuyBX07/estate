package com.example.estate.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "properties")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Property {
    @Id
    private String id;

    private String title;
    private String address;
    private String city;
    private String seller;
    private String numberPhone;
    private Long price;
    private Double area;
    private Double unit_Price;
    private LocalDateTime postedDate;
    private String link;
    private String legal;
    private Float frontage;   // đổi từ float → Float
    private String type;
    private Integer bedroom;  // đổi từ int → Integer
    private Integer bathroom; // đổi từ int → Integer
    private String amenityLocation;
    private String source;
}
