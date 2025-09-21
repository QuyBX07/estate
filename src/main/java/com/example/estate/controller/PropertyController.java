package com.example.estate.controller;

import com.example.estate.dto.*;
import com.example.estate.entity.Property;
import com.example.estate.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;

    // Lấy tất cả properties
    @GetMapping
    public ResponseEntity<List<PropertyDTO>> getAllProperties() {
        return ResponseEntity.ok(propertyService.getAllProperties());
    }

    // Lấy property theo ID
    @GetMapping("/{id}")
    public ResponseEntity<PropertyDTO> getPropertyById(@PathVariable String id) {
        PropertyDTO dto = propertyService.getPropertyById(id);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    // Thêm property mới
    @PostMapping
    public ResponseEntity<PropertyDTO> createProperty(@RequestBody PropertyDTO propertyDTO) {
        PropertyDTO saved = propertyService.saveProperty(propertyDTO);
        return ResponseEntity.ok(saved);
    }

    // Cập nhật property
    @PutMapping("/{id}")
    public ResponseEntity<PropertyDTO> updateProperty(@PathVariable String id,
                                                      @RequestBody PropertyDTO propertyDTO) {
        // Đảm bảo DTO có id đúng với param
        propertyDTO.setId(id);
        PropertyDTO updated = propertyService.saveProperty(propertyDTO);
        return ResponseEntity.ok(updated);
    }

    // Xóa property
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable String id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }

    // chuc nang bien doi gia theo thoi gian
    @GetMapping("/analytics/average-price")
    public ResponseEntity<List<PropertyDTO>> getAveragePriceByMonth() {
        propertyService.getAveragePriceByMonth();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/statistics/cities")
    public ResponseEntity<List<CityStatisticsDTO>> getCityStatistics() {
        return ResponseEntity.ok(propertyService.getCityStatistics());
    }

    @GetMapping("topsellers")
    public ResponseEntity<List<TopSellerDTO>> getTopSlellers(){
        return ResponseEntity.ok(propertyService.getTopSellers());
    }

    @GetMapping("TypeDistribution")
    public ResponseEntity<List<TypeDistributionDTO>> getTyprDistribution(){
        return ResponseEntity.ok(propertyService.getTypeDistribution());
    }

    @GetMapping("Month")
    public ResponseEntity<List<MonthlyPriceTrendDTO>> getMonthlyPriceTrend(){
        return ResponseEntity.ok(propertyService.getMonthlyPriceTrend());
    }

    @GetMapping("topwebsite")
    public ResponseEntity<List<WebsiteStatsDTO>> getTopWebsite(){
        return ResponseEntity.ok(propertyService.getTopWebsite());
    }

    @GetMapping("priceallocation")
    public ResponseEntity<List<PriceAllocationDTO>> getPriceAllocation(){
        return ResponseEntity.ok((propertyService.getPriceAllocation()));
    }

    @GetMapping("/options")
    public ResponseEntity<PropertyOptionsDTO> getOptions() {
        return ResponseEntity.ok(propertyService.getOptions());
    }

    @GetMapping("/filter")
    public List<Property> filter(
            @RequestParam(required = false) List<String> types,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) Long minPrice,
            @RequestParam(required = false) Long maxPrice,
            @RequestParam(required = false) Integer minArea,
            @RequestParam(required = false) Integer maxArea,
            @RequestParam(required = false) String sort
    ) {
        return propertyService.filter(types, city, minPrice, maxPrice, minArea, maxArea, sort);
    }
}
