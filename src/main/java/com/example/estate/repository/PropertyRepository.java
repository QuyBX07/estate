package com.example.estate.repository;

import com.example.estate.dto.AveragePriceDTO;
import com.example.estate.entity.Property;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface PropertyRepository extends MongoRepository<Property, String> {

    @Aggregation(pipeline = {
            "{ $group: { " +
                    "  _id: { city: '$city', year: { $year: '$postedDate' }, month: { $month: '$postedDate' } }, " +
                    "  avgPrice: { $avg: '$unitPrice' }, " +
                    "  count: { $sum: 1 } " +
                    "} }",
            "{ $project: { " +
                    "  city: '$_id.city', " +
                    "  year: '$_id.year', " +
                    "  month: '$_id.month', " +
                    "  avgPrice: 1, " +
                    "  count: 1, " +
                    "  _id: 0 " +
                    "} }",
            "{ $sort: { year: 1, month: 1 } }"
    })
    List<AveragePriceDTO> getAveragePriceByMonth();

    // 7 ngày qua (group by ngày)
    @Aggregation(pipeline = {
            "{ $match: { postedDate: { $gte: ?0 } } }",
            "{ $group: { _id: { day: { $dayOfMonth: '$postedDate' }, month: { $month: '$postedDate' } }, count: { $sum: 1 } } }",
            "{ $sort: { '_id.month': 1, '_id.day': 1 } }"
    })
    List<Map<String, Object>> getPostStatsLast7Days(Date startDate);

    // Theo tuần trong tháng
    @Aggregation(pipeline = {
            "{ $match: { $expr: { $and: [ { $eq: [ { $year: '$postedDate' }, ?0 ] }, { $eq: [ { $month: '$postedDate' }, ?1 ] } ] } } }",
            "{ $group: { _id: { week: { $week: '$postedDate' } }, count: { $sum: 1 } } }",
            "{ $sort: { '_id.week': 1 } }"
    })
    List<Map<String, Object>> getPostStatsByWeeks(int year, int month);

    // Theo năm (12 tháng)
    @Aggregation(pipeline = {
            "{ $match: { $expr: { $eq: [ { $year: '$postedDate' }, ?0 ] } } }",
            "{ $group: { _id: { month: { $month: '$postedDate' } }, count: { $sum: 1 } } }",
            "{ $sort: { '_id.month': 1 } }"
    })
    List<Map<String, Object>> getPostStatsByYear(int year);

    // gia theo ngay
    @Aggregation(pipeline = {
            "{ $match: { postedDate: { $gte: ?0 } } }",
            "{ $group: { _id: { day: { $dayOfMonth: '$postedDate' }, month: { $month: '$postedDate' } }, avgPrice: { $avg: '$price' } } }",
            "{ $sort: { '_id.month': 1, '_id.day': 1 } }"
    })
    List<Map<String, Object>> getAvgPriceLast7Days(Date startDate);

    // gia theo tuan
    @Aggregation(pipeline = {
            "{ $match: { $expr: { $and: [ { $eq: [{ $year: '$postedDate' }, ?0] }, { $eq: [{ $month: '$postedDate' }, ?1] } ] } } }",
            "{ $group: { _id: { week: { $week: '$postedDate' } }, avgPrice: { $avg: '$price' } } }",
            "{ $sort: { '_id.week': 1 } }"
    })
    List<Map<String, Object>> getAvgPriceByWeeks(int year, int month);

    //gia theo năm
    @Aggregation(pipeline = {
            "{ $match: { $expr: { $eq: [{ $year: '$postedDate' }, ?0] } } }",
            "{ $group: { _id: { month: { $month: '$postedDate' } }, avgPrice: { $avg: '$price' } } }",
            "{ $sort: { '_id.month': 1 } }"
    })
    List<Map<String, Object>> getAvgPriceByYear(int year);

}