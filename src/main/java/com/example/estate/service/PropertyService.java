package com.example.estate.service;

import com.example.estate.dto.PropertyDTO;

import java.util.List;

public interface PropertyService {
    List<PropertyDTO> getAllProperties();
    PropertyDTO getPropertyById(String id);
    PropertyDTO saveProperty(PropertyDTO propertyDTO);
    void deleteProperty(String id);
}
