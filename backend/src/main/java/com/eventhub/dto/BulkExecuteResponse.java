package com.eventhub.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BulkExecuteResponse {

    private long totalDurationMs;
    private List<ExecutionResult> results;
}
