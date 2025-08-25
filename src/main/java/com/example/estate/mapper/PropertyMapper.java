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
                .price(dto.getPrice())
                .area(dto.getArea())
                .room(dto.getRoom())
                .bedroom(dto.getBedroom())
                .seller(dto.getSeller())
                .phone(dto.getPhone())
                .link(dto.getLink())
                .datePost(dto.getDatePost())
                .build();
    }

    public static PropertyDTO toDTO(Property entity) {
        if (entity == null) return null;
        return PropertyDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .address(entity.getAddress())
                .city(entity.getCity())
                .price(entity.getPrice())
                .area(entity.getArea())
                .room(entity.getRoom())
                .bedroom(entity.getBedroom())
                .seller(entity.getSeller())
                .phone(entity.getPhone())
                .link(entity.getLink())
                .datePost(entity.getDatePost())
                .build();
    }
}
