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

    private String title;       // Tiêu đề
    private String address;     // Địa chỉ
    private String city;        // Thành phố
    private String seller;      // Người bán
    private String phone;       // SĐT
    private Long price;         // Giá
    private Double area;        // Diện tích
    private Double unitPrice;   // Đơn giá (Giá/Diện tích)
    private LocalDateTime postedDate; // Ngày đăng
    private String link;        // Link tin
    private Boolean legalStatus; // Pháp lý (có = true, không = false)
    private Integer facade;     // Mặt tiền (0,1,2,...)
    private String type;        // Loại hình (chung cư, nhà phố, biệt thự, shophouse,...)
}
