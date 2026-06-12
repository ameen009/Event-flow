package com.eventhub.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class BulkExecuteRequest {

    @NotEmpty(message = "taskIds must not be empty")
    private List<Long> taskIds;
}
