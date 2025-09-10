package com.example.estate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeStatDTO {
    private String label;     // VD: "15/01", "Tuần 2", "Tháng 3"
    private long totalPosts;  // số tin
}
