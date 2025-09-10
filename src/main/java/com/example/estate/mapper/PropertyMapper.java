package com.example.estate.mapper;

import com.example.estate.dto.PropertyDTO;
import com.example.estate.entity.Property;

public class PropertyMapper {

    public static Property toEntity(PropertyDTO dto) {
        if (dto == null) return null;
        return Property.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .address(dto.getAddress())
                .city(dto.getCity())
                .seller(dto.getSeller())
                .phone(dto.getPhone())
                .price(dto.getPrice())
                .area(dto.getArea())
                .unitPrice(dto.getUnitPrice())
                .postedDate(dto.getPostedDate())
                .link(dto.getLink())
                .legalStatus(dto.getLegalStatus())
                .facade(dto.getFacade())
                .type(dto.getType())
                .build();
    }

    public static PropertyDTO toDTO(Property entity) {
        if (entity == null) return null;
        return PropertyDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .address(entity.getAddress())
                .city(entity.getCity())
                .seller(entity.getSeller())
                .phone(entity.getPhone())
                .price(entity.getPrice())
                .area(entity.getArea())
                .unitPrice(entity.getUnitPrice())
                .postedDate(entity.getPostedDate())
                .link(entity.getLink())
                .legalStatus(entity.getLegalStatus())
                .facade(entity.getFacade())
                .type(entity.getType())
                .build();
    }
}
