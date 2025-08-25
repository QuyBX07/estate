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
    private String id;   // ánh xạ _id trong MongoDB
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

