package com.example.ticketsystem.repository;

import com.example.ticketsystem.model.SystemConfiguration;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigurationRepository extends MongoRepository<SystemConfiguration, String> {
}
