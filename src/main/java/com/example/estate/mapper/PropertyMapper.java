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
                .numberPhone(dto.getNumberPhone())
                .price(dto.getPrice())
                .area(dto.getArea())
                .unit_Price(dto.getUnit_Price())
                .postedDate(dto.getPostedDate())
                .link(dto.getLink())
                .legal(dto.getLegal())
                .frontage(dto.getFrontage())
                .type(dto.getType())
                .bedroom(dto.getBedroom())
                .bathroom(dto.getBathroom())
                .amenityLocation(dto.getAmenityLocation())
                .source(dto.getSource())
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
                .numberPhone(entity.getNumberPhone())
                .price(entity.getPrice())
                .area(entity.getArea())
                .unit_Price(entity.getUnit_Price())
                .postedDate(entity.getPostedDate())
                .link(entity.getLink())
                .legal(entity.getLegal())
                .frontage(entity.getFrontage())
                .type(entity.getType())
                .bedroom(entity.getBedroom())
                .bathroom(entity.getBathroom())
                .amenityLocation(entity.getAmenityLocation())
                .source(entity.getSource())
                .build();
    }
}
