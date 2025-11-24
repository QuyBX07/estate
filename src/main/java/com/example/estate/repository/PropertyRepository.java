package com.example.estate.repository;

import com.example.estate.dto.AveragePriceDTO;
import com.example.estate.dto.PropertyTypeSummaryDTO;
import com.example.estate.dto.PropertyTypeTrendDTO;
import com.example.estate.dto.*;
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


    // thong ke theo loai hinh
    @Aggregation(pipeline = {
            "{ $group: { " +
                    "  _id: '$type', " +
                    "  totalListings: { $sum: 1 }, " +
                    "  avgPrice: { $avg: '$price' }, " +
                    "  avgArea: { $avg: '$area' }, " +
                    "  minPrice: { $min: '$price' }, " +
                    "  maxPrice: { $max: '$price' }, " +
                    "  cities: { $push: '$city' } " +
                    "} }",
            "{ $project: { " +
                    "  type: '$_id', " +
                    "  totalListings: 1, " +
                    "  avgPrice: 1, " +
                    "  avgArea: 1, " +
                    "  minPrice: 1, " +
                    "  maxPrice: 1, " +
                    "  cities: 1 " +
                    "} }"
    })
    List<PropertyTypeSummaryDTO> aggregateByType();

    //trend theo lại hinh bat dong san
    @Aggregation(pipeline = {
            "{ $match: { postedDate: { $gte: ?0 } } }",
            "{ $group: { " +
                    " _id: '$type', " +
                    " count: { $sum: 1 }, " +
                    " avgPrice: { $avg: '$price' }, " +
                    " avgArea: { $avg: '$area' } " +
                    "} }",
            "{ $project: { " +
                    " type: '$_id', " +
                    " count: 1, " +
                    " avgPrice: { $ifNull: ['$avgPrice', 0] }, " +
                    " avgArea: { $ifNull: ['$avgArea', 0] }, " +
                    " _id: 0 " +
                    "} }",
            "{ $sort: { count: -1 } }"
    })
    List<PropertyTypeTrendDTO> aggregateTypeSummaryLast7Days(Date fromDate);

    @Aggregation(pipeline = {
            // Bỏ qua những record không có city, type hoặc price
            "{ $match: { city: { $exists: true, $ne: null, $ne: \"\" }, " +
                    "           type: { $exists: true, $ne: null, $ne: \"\" }, " +
                    "           price: { $exists: true, $ne: null } } }",

            // Gom nhóm theo city + type
            "{ $group: { " +
                    "   _id: { city: \"$city\", type: \"$type\" }, " +
                    "   count: { $sum: 1 }, " +
                    "   avgPrice: { $avg: \"$price\" } " +
                    "} }",

            "{ $sort: { count: -1 } }",

            "{ $group: { " +
                    "   _id: \"$_id.city\", " +
                    "   postcount: { $sum: \"$count\" }, " +
                    "   popularType: { $first: \"$_id.type\" }, " +
                    "   averagePrice: { $avg: \"$avgPrice\" } " +
                    "} }",

            "{ $project: { " +
                    "   _id: 0, " +
                    "   city: \"$_id\", " +
                    "   postcount: 1, " +
                    "   averagePrice: 1, " +
                    "   popularType: 1 " +
                    "} }"
    })
    List<CityStatisticsDTO> getCityStatistics();


    @Aggregation(pipeline = {
            "{ $match: { seller: { $nin: [null, ''] } } }",
            "{ $group: { " +
                    "_id: { seller: '$seller', phone: '$numberPhone' }, " +
                    "postCount: { $sum: 1 }, " +
                    "totalPrice: { $sum: '$price' } " +
                    "} }",
            "{ $project: { " +
                    "_id: 0, " +
                    "seller: '$_id.seller', " +
                    "phone: '$_id.phone', " +
                    "postCount: 1, " +
                    "totalPrice: 1 " +
                    "} }",
            "{ $sort: { postCount: -1 } }",
            "{ $limit: 5 }"
    })
    List<TopSellerDTO> getTopSellers();

    @Aggregation(pipeline = {
            "{ $group: { _id: \"$type\", postcount: { $sum: 1 } } }",
            "{ $group: { _id: null, total: { $sum: \"$postcount\" }, data: { $push: { type: \"$_id\", postcount: \"$postcount\" } } } }",
            "{ $unwind: \"$data\" }",
            "{ $project: { _id: 0, type: \"$data.type\", postcount: \"$data.postcount\", percent: { $multiply: [ { $divide: [ \"$data.postcount\", \"$total\" ] }, 100 ] } } }"
    })
    List<TypeDistributionDTO> getTypeDistribution();

    @Aggregation(pipeline = {
            // Loại bỏ document không có postedDate hoặc postedDate = null
            "{ $match: { postedDate: { $exists: true, $ne: null } } }",

            "{ $group: { " +
                    "    _id: { year: { $year: \"$postedDate\" }, month: { $month: \"$postedDate\" } }, " +
                    "    averagePrice: { $avg: \"$price\" }, " +
                    "    postcount: { $sum: 1 } " +
                    "} }",
            "{ $sort: { \"_id.year\": 1, \"_id.month\": 1 } }",
            "{ $project: { " +
                    "    _id: 0, " +
                    "    year: \"$_id.year\", " +
                    "    month: \"$_id.month\", " +
                    "    averagePrice: 1, " +
                    "    postcount: 1 " +
                    "} }"
    })
    List<MonthlyPriceTrendDTO> getPriceTrendByMonth();

    @Aggregation(pipeline = {
            "{ $addFields: { " +
                    "    domain: { $arrayElemAt: [ { $split: [ \"$link\", \"/\" ] }, 2 ] } " +
                    "} }",
            "{ $group: { " +
                    "    _id: \"$domain\", " +
                    "    postcount: { $sum: 1 }, " +
                    "    averagePrice: { $avg: \"$price\" } " +
                    "} }",
            "{ $group: { " +
                    "    _id: null, " +
                    "    total: { $sum: \"$postcount\" }, " +
                    "    data: { $push: \"$$ROOT\" } " +
                    "} }",
            "{ $unwind: \"$data\" }",
            "{ $project: { " +
                    "    _id: 0, " +
                    "    website: \"$data._id\", " +
                    "    postcount: \"$data.postcount\", " +
                    "    averagePrice: \"$data.averagePrice\", " +
                    "    percent: { $multiply: [ { $divide: [ \"$data.postcount\", \"$total\" ] }, 100 ] } " +
                    "} }",
            "{ $sort: { postcount: -1 } }"
    })
    List<WebsiteStatsDTO> getTopWebsite();

    @Aggregation(pipeline = {
            "{ $match: { price: { $exists: true, $ne: null, $gt: 0 } } }", // bỏ giá <= 0

            "{ $bucket: { " +
                    "    groupBy: \"$price\", " +
                    "    boundaries: [2000000000, 5000000000, 10000000000, 20000000000, 100000000000], " +
                    "    default: 100000000001, " +
                    "    output: { count: { $sum: 1 } } " +
                    "} }",

            "{ $group: { " +
                    "    _id: null, " +
                    "    total: { $sum: \"$count\" }, " +
                    "    data: { $push: { range: { $toLong: \"$_id\" }, count: \"$count\" } } " +
                    "} }",

            "{ $unwind: \"$data\" }",

            "{ $project: { " +
                    "    _id: 0, " +
                    "    price: \"$data.range\", " +
                    "    percent: { $round: [ { $multiply: [ { $divide: [\"$data.count\", \"$total\"] }, 100 ] }, 2 ] } " +
                    "} }"
    })
    List<PriceAllocationDTO> getPriceAllocation();


}
