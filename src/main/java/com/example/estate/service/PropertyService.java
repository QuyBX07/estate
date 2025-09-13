package com.example.estate.service;

import com.example.estate.dto.*;

import java.util.List;

public interface PropertyService {
    List<PropertyDTO> getAllProperties();
    PropertyDTO getPropertyById(String id);
    PropertyDTO saveProperty(PropertyDTO propertyDTO);
    void deleteProperty(String id);
    List<CityStatisticsDTO> getCityStatistics();
    List<TopSellerDTO> getTopSellers();
    List<TypeDistributionDTO> getTypeDistribution();
    List<MonthlyPriceTrendDTO> getMonthlyPriceTrend();
    List<WebsiteStatsDTO> getTopWebsite();
}
