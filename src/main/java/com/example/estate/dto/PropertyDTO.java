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
    private Long price;
    private String area;
    private Integer room;
    private Integer bedroom;
    private String seller;
    private String phone;
    private String link;
    private LocalDateTime datePost;
}
