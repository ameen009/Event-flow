package com.eventhub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Map;

@Data
public class TaskRequest {

    @NotBlank(message = "Task name is required")
    private String name;

    @NotBlank(message = "Cron expression is required")
    private String cronExpression;

    @NotBlank(message = "Task type is required")
    private String type;

    private Map<String, Object> payload;

    @Pattern(regexp = "ACTIVE|INACTIVE", message = "Status must be ACTIVE or INACTIVE")
    private String status;
}
