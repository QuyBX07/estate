package com.example.estate.repository;

import com.example.estate.dto.*;
import com.example.estate.entity.Property;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

public interface PropertyRepository extends MongoRepository<Property, String> {

    @Aggregation(pipeline = {
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
            "{ $group: { " +
                    "   _id: { seller: \"$seller\", phone: \"$phone\" }, " +
                    "   postCount: { $sum: 1 }, " +
                    "   totalPrice: { $sum: \"$price\" } " +
                    "} }",
            "{ $project: { " +
                    "   _id: 0, " +
                    "   seller: \"$_id.seller\", " +
                    "   phone: \"$_id.phone\", " +
                    "   postCount: 1, " +
                    "   totalPrice: 1 " +
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


}
