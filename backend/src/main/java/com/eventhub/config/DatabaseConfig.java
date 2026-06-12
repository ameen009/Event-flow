package com.eventhub.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.eventhub.repository.jpa")
@EnableMongoRepositories(basePackages = "com.eventhub.repository.mongo")
public class DatabaseConfig {
}
