package com.example.estate.service.impl;

import com.example.estate.dto.*;
import com.example.estate.entity.Property;
import com.example.estate.mapper.PropertyMapper;
import com.example.estate.repository.PropertyRepository;
import com.example.estate.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;

    @Override
    public List<PropertyDTO> getAllProperties() {
        return propertyRepository.findAll()
                .stream()
                .map(PropertyMapper::toDTO) // mapper static method
                .collect(Collectors.toList());
    }

    @Override
    public PropertyDTO getPropertyById(String id) {
        return propertyRepository.findById(id)
                .map(PropertyMapper::toDTO)
                .orElse(null);
    }

    @Override
    public PropertyDTO saveProperty(PropertyDTO propertyDTO) {
        Property property = PropertyMapper.toEntity(propertyDTO);
        Property saved = propertyRepository.save(property);
        return PropertyMapper.toDTO(saved);
    }

    @Override
    public void deleteProperty(String id) {
        propertyRepository.deleteById(id);
    }

    @Override
    public List<CityStatisticsDTO> getCityStatistics() {
        return propertyRepository.getCityStatistics();
    }

    @Override
    public List<TopSellerDTO> getTopSellers(){
        return propertyRepository.getTopSellers();
    }
    @Override
    public List<TypeDistributionDTO> getTypeDistribution(){
        return propertyRepository.getTypeDistribution();
    }
    @Override
    public List<MonthlyPriceTrendDTO> getMonthlyPriceTrend(){
        return propertyRepository.getPriceTrendByMonth();
    }
    @Override
    public List<WebsiteStatsDTO> getTopWebsite(){
        return propertyRepository.getTopWebsite();
    }
}
