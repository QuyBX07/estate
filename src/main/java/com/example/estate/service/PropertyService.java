package com.example.estate.service;

import com.example.estate.dto.*;

import java.util.List;

public interface PropertyService {
    List<PropertyDTO> getAllProperties();
    PropertyDTO getPropertyById(String id);
    PropertyDTO saveProperty(PropertyDTO propertyDTO);
    void deleteProperty(String id);

    // chưc nang bien doi gia theo thoi gian
    List<AveragePriceDTO> getAveragePriceByMonth();

    List<TimeStatDTO> getPostStatsLast7Days();
    List<TimeStatDTO> getPostStatsByWeeks(int year, int month);
    List<TimeStatDTO> getPostStatsByYear(int year);

    List<PriceStatDTO> getAvgPriceLast7Days();
    List<PriceStatDTO> getAvgPriceByWeeks(int year, int month);
    List<PriceStatDTO> getAvgPriceByYear(int year);

    List<PropertyTypeSummaryDTO> getPropertyTypeSummary();
    //trend theo loai hinh
    List<PropertyTypeTrendDTO> getPropertyTypeTrendLast7Days();
}
