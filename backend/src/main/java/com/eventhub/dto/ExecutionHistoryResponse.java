package com.eventhub.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class ExecutionHistoryResponse {

    private Long id;
    private Long taskId;
    private String status;
    private Instant startTime;
    private Instant endTime;
    private Long executionDuration;
    private String errorMessage;
}
