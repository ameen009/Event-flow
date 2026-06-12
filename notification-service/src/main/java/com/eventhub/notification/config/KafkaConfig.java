package com.eventhub.notification.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@EnableKafka
public class KafkaConfig {
    // Consumer configuration is handled via application.yml
    // Spring Boot auto-configures the consumer factory from properties
}
