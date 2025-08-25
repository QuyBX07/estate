package com.example.estate.repository;

import com.example.estate.entity.Property;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends MongoRepository<Property, String> {
}
