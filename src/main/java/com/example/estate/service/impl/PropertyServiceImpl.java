package com.example.estate.service.impl;

import com.example.estate.dto.*;
import com.example.estate.entity.Property;
import com.example.estate.mapper.PropertyMapper;
import com.example.estate.repository.PropertyRepository;
import com.example.estate.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;

    private final MongoTemplate mongoTemplate;

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

    // chc nang bien doi gai theo thoi gian
    @Override
    public List<AveragePriceDTO> getAveragePriceByMonth() {
        List<AveragePriceDTO> results = propertyRepository.getAveragePriceByMonth();
        System.out.println(">>> AveragePrice results: " + results);
        return results;
    }

    @Override
    public List<TimeStatDTO> getPostStatsLast7Days() {
        LocalDate today = LocalDate.now();
        LocalDate sevenDaysAgo = today.minusDays(6);

        // convert LocalDate -> java.util.Date
        Date startDate = Date.from(sevenDaysAgo.atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<Map<String, Object>> results =
                propertyRepository.getPostStatsLast7Days(startDate);

        // Map kết quả DB
        Map<String, Long> countByDay = results.stream()
                .collect(Collectors.toMap(
                        r -> {
                            Integer day = (Integer) ((Map<?, ?>) r.get("_id")).get("day");
                            Integer month = (Integer) ((Map<?, ?>) r.get("_id")).get("month");
                            return String.format("%02d/%02d", day, month);
                        },
                        r -> ((Number) r.get("count")).longValue()
                ));

        // Trả về đủ 7 ngày
        List<TimeStatDTO> finalResult = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            LocalDate d = sevenDaysAgo.plusDays(i);
            String label = d.format(DateTimeFormatter.ofPattern("dd/MM"));
            long count = countByDay.getOrDefault(label, 0L);
            finalResult.add(new TimeStatDTO(label, count));
        }

        return finalResult;
    }

    @Override
    public List<TimeStatDTO> getPostStatsByWeeks(int year, int month) {
        List<Map<String, Object>> results = propertyRepository.getPostStatsByWeeks(year, month);
        return results.stream()
                .map(r -> new TimeStatDTO(
                        "Tuần " + ((Map<?, ?>) r.get("_id")).get("week"),
                        ((Number) r.get("count")).longValue()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<TimeStatDTO> getPostStatsByYear(int year) {
        List<Map<String, Object>> results = propertyRepository.getPostStatsByYear(year);
        return results.stream()
                .map(r -> new TimeStatDTO(
                        "Tháng " + ((Map<?, ?>) r.get("_id")).get("month"),
                        ((Number) r.get("count")).longValue()
                ))
                .collect(Collectors.toList());
    }

    //theo 7 nagy
    @Override
    public List<PriceStatDTO> getAvgPriceLast7Days() {
        LocalDate today = LocalDate.now();
        LocalDate sevenDaysAgo = today.minusDays(6);

        // convert sang java.util.Date để Mongo hiểu
        Date startDate = java.sql.Date.valueOf(sevenDaysAgo);

        List<Map<String, Object>> results =
                propertyRepository.getAvgPriceLast7Days(startDate);

        Map<String, Double> avgByDay = results.stream()
                .collect(Collectors.toMap(
                        r -> {
                            Integer day = (Integer) ((Map) r.get("_id")).get("day");
                            Integer month = (Integer) ((Map) r.get("_id")).get("month");
                            return String.format("%02d/%02d", day, month);
                        },
                        r -> r.get("avgPrice") != null ? ((Number) r.get("avgPrice")).doubleValue() : 0.0
                ));

        List<PriceStatDTO> finalResult = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            LocalDate d = sevenDaysAgo.plusDays(i);
            String label = d.format(DateTimeFormatter.ofPattern("dd/MM"));
            double avg = avgByDay.getOrDefault(label, 0.0);
            finalResult.add(new PriceStatDTO(label, avg));
        }
        return finalResult;
    }

    @Override
    public List<PriceStatDTO> getAvgPriceByWeeks(int year, int month) {
        List<Map<String, Object>> results = propertyRepository.getAvgPriceByWeeks(year, month);

        Map<Integer, Double> avgByWeek = results.stream()
                .collect(Collectors.toMap(
                        r -> (Integer) ((Map) r.get("_id")).get("week"),
                        r -> ((Number) r.get("avgPrice")).doubleValue()
                ));

        // Tính số tuần trong tháng
        YearMonth ym = YearMonth.of(year, month);
        int daysInMonth = ym.lengthOfMonth();
        LocalDate firstDay = LocalDate.of(year, month, 1);
        int firstWeek = firstDay.get(WeekFields.ISO.weekOfWeekBasedYear());
        int lastWeek = LocalDate.of(year, month, daysInMonth)
                .get(WeekFields.ISO.weekOfWeekBasedYear());

        List<PriceStatDTO> finalResult = new ArrayList<>();
        for (int w = firstWeek; w <= lastWeek; w++) {
            String label = "Tuần " + w;
            double avg = avgByWeek.getOrDefault(w, 0.0);
            finalResult.add(new PriceStatDTO(label, avg));
        }
        return finalResult;
    }

    @Override
    public List<PriceStatDTO> getAvgPriceByYear(int year) {
        List<Map<String, Object>> results = propertyRepository.getAvgPriceByYear(year);

        Map<Integer, Double> avgByMonth = results.stream()
                .collect(Collectors.toMap(
                        r -> (Integer) ((Map) r.get("_id")).get("month"),
                        r -> ((Number) r.get("avgPrice")).doubleValue()
                ));

        List<PriceStatDTO> finalResult = new ArrayList<>();
        for (int m = 1; m <= 12; m++) {
            String label = "Tháng " + m;
            double avg = avgByMonth.getOrDefault(m, 0.0);
            finalResult.add(new PriceStatDTO(label, avg));
        }
        return finalResult;
    }
    //
    @Override
    public List<PropertyTypeSummaryDTO> getPropertyTypeSummary() {
        List<PropertyTypeSummaryDTO> raw = propertyRepository.aggregateByType();

        long total = raw.stream()
                .mapToLong(PropertyTypeSummaryDTO::getTotalListings)
                .sum();

        if (total == 0) {
            return raw; // không có dữ liệu thì trả thẳng
        }

        return raw.stream().map(dto -> {
            // Thị phần %
            dto.setMarketShare((double) dto.getTotalListings() / total * 100);

            // Tìm city hot (lọc null trước)
            if (dto.getCities() != null && !dto.getCities().isEmpty()) {
                Map<String, Long> cityCount = dto.getCities().stream()
                        .filter(Objects::nonNull) // 👈 bỏ city null
                        .collect(Collectors.groupingBy(c -> c, Collectors.counting()));

                String hotCity = cityCount.entrySet().stream()
                        .max(Map.Entry.comparingByValue())
                        .map(Map.Entry::getKey)
                        .orElse(null);

                dto.setHotCity(hotCity);
            } else {
                dto.setHotCity(null);
            }

            dto.setCities(null); // xoá cho gọn, không trả ra frontend
            return dto;
        }).collect(Collectors.toList());
    }

    // trend theo loai hinh
    @Override
    public List<PropertyTypeTrendDTO> getPropertyTypeTrendLast7Days() {
        LocalDate sevenDaysAgo = LocalDate.now().minusDays(6);
        Date fromDate = Date.from(sevenDaysAgo.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return propertyRepository.aggregateTypeSummaryLast7Days(fromDate);
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

    @Override
    public  List<PriceAllocationDTO> getPriceAllocation(){
        return propertyRepository.getPriceAllocation();
    }

    @Override
    public PropertyOptionsDTO getOptions() {
        List<Property> all = propertyRepository.findAll();

        List<String> cities = all.stream()
                .map(Property::getCity)
                .filter(c -> c != null && !c.isBlank())
                .distinct()
                .collect(Collectors.toList());

        List<String> types = all.stream()
                .map(Property::getType)
                .filter(t -> t != null && !t.isBlank())
                .distinct()
                .collect(Collectors.toList());

        PropertyOptionsDTO dto = new PropertyOptionsDTO();
        dto.setCities(cities);
        dto.setTypes(types);

        return dto;
    }

    //fillter
    @Override
    public List<Property> filter(
            List<String> types,
            String city,
            Long minPrice,
            Long maxPrice,
            Integer minArea,
            Integer maxArea,
            String sort
    ) {
        Query query = new Query();

        // Loại hình
        if (types != null && !types.isEmpty()) {
            query.addCriteria(Criteria.where("type").in(types));
        }

        // Thành phố
        if (city != null && !city.isEmpty()) {
            query.addCriteria(Criteria.where("city").regex(city, "i"));
        }

        // Khoảng giá linh hoạt
        if (minPrice != null && maxPrice != null) {
            query.addCriteria(Criteria.where("price").gte(minPrice).lte(maxPrice));
        } else if (minPrice != null) {
            query.addCriteria(Criteria.where("price").gte(minPrice));
        } else if (maxPrice != null) {
            query.addCriteria(Criteria.where("price").lte(maxPrice));
        }

        // Khoảng diện tích linh hoạt
        if (minArea != null && maxArea != null) {
            query.addCriteria(Criteria.where("area").gte(minArea).lte(maxArea));
        } else if (minArea != null) {
            query.addCriteria(Criteria.where("area").gte(minArea));
        } else if (maxArea != null) {
            query.addCriteria(Criteria.where("area").lte(maxArea));
        }

        // Sort
        if ("priceAsc".equals(sort)) query.with(Sort.by(Sort.Direction.ASC, "price"));
        if ("priceDesc".equals(sort)) query.with(Sort.by(Sort.Direction.DESC, "price"));
        if ("areaAsc".equals(sort)) query.with(Sort.by(Sort.Direction.ASC, "area"));
        if ("areaDesc".equals(sort)) query.with(Sort.by(Sort.Direction.DESC, "area"));
        if ("dateAsc".equals(sort)) query.with(Sort.by(Sort.Direction.ASC, "postedDate"));
        if ("dateDesc".equals(sort)) query.with(Sort.by(Sort.Direction.DESC, "postedDate"));


        return mongoTemplate.find(query, Property.class);
    }

}
