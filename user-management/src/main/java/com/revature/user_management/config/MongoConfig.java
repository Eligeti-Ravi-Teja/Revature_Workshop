package com.revature.user_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * MongoConfig - MongoDB Configuration
 * This class demonstrates:
 * - MongoDB configuration
 * - Custom MongoDB settings
 * - Repository scanning configuration
 * - Bean definitions for MongoDB
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.revature.user_management.repository")
public class MongoConfig {
    
    /**
     * Configure MongoDB mapping context
     * This allows custom MongoDB mapping configurations
     */
    @Bean
    public MongoMappingContext mongoMappingContext() {
        MongoMappingContext context = new MongoMappingContext();
        context.setAutoIndexCreation(true);
        return context;
    }
} 