package com.eventhub.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExecutionResult {

    private Long taskId;
    private String status;      // SUCCESS | FAILED
    private long durationMs;
    private String error;       // null on success
}
