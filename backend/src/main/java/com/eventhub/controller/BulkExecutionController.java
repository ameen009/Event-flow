package com.eventhub.controller;

import com.eventhub.dto.BulkExecuteRequest;
import com.eventhub.dto.BulkExecuteResponse;
import com.eventhub.service.BulkExecutionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class BulkExecutionController {

    private final BulkExecutionService bulkExecutionService;

    @PostMapping("/bulk-execute")
    public ResponseEntity<BulkExecuteResponse> bulkExecute(@Valid @RequestBody BulkExecuteRequest request) {
        return ResponseEntity.ok(bulkExecutionService.executeWithExecutorService(request));
    }
}
