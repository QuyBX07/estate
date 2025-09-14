package com.example.estate.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyOptionsDTO {
    private List<String> cities;
    private List<String> types;
}
