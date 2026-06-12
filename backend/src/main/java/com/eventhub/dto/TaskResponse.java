package com.eventhub.dto;

import lombok.Data;

import java.time.Instant;
import java.util.Map;

@Data
public class TaskResponse {

    private Long id;
    private String name;
    private String cronExpression;
    private String type;
    private Map<String, Object> payload;
    private String status;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
}
