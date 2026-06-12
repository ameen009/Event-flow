package com.eventhub.service;

import com.eventhub.dto.BulkExecuteRequest;
import com.eventhub.dto.BulkExecuteResponse;
import com.eventhub.dto.ExecutionResult;
import com.eventhub.entity.TaskExecutionHistoryEntity;
import com.eventhub.exception.ResourceNotFoundException;
import com.eventhub.repository.jpa.TaskExecutionHistoryRepository;
import com.eventhub.repository.jpa.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
@Slf4j
public class BulkExecutionService {

    private final TaskRepository taskRepository;
    private final TaskExecutionHistoryRepository historyRepository;

    private static final ExecutorService pool = Executors.newFixedThreadPool(10);
    private static final Random random = new Random();

    public BulkExecuteResponse executeWithExecutorService(BulkExecuteRequest request) {
        long start = System.currentTimeMillis();

        // Submit one Callable per taskId — all start immediately in parallel
        List<Future<ExecutionResult>> futures = request.getTaskIds().stream()
                .map(taskId -> pool.submit((Callable<ExecutionResult>) () -> runTask(taskId)))
                .toList();

        // Block until every future is done, collect results
        List<ExecutionResult> results = new ArrayList<>();
        for (Future<ExecutionResult> future : futures) {
            try {
                results.add(future.get());
            } catch (ExecutionException e) {
                // Should not reach here — runTask handles its own exceptions internally
                log.error("Unexpected error collecting future result", e);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.error("Interrupted while waiting for task result", e);
            }
        }

        return BulkExecuteResponse.builder()
                .totalDurationMs(System.currentTimeMillis() - start)
                .results(results)
                .build();
    }

    private ExecutionResult runTask(Long taskId) {
        String thread = Thread.currentThread().getName();
        long start = System.currentTimeMillis();

        log.info("Executing taskId={} on thread={}", taskId, thread);

        TaskExecutionHistoryEntity history = TaskExecutionHistoryEntity.builder()
                .taskId(taskId)
                .status("RUNNING")
                .startTime(Instant.now())
                .build();
        historyRepository.save(history);

        try {
            // Verify task exists
            taskRepository.findById(taskId)
                    .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));

            // Simulate work: sleep 1–3 seconds
            int sleepMs = 1000 + random.nextInt(2000);
            Thread.sleep(sleepMs);

            long durationMs = System.currentTimeMillis() - start;
            history.setStatus("SUCCESS");
            history.setEndTime(Instant.now());
            history.setExecutionDuration(durationMs);
            historyRepository.save(history);

            log.info("Completed taskId={} in {}ms on thread={}", taskId, durationMs, thread);
            return ExecutionResult.builder()
                    .taskId(taskId)
                    .status("SUCCESS")
                    .durationMs(durationMs)
                    .build();

        } catch (Exception e) {
            long durationMs = System.currentTimeMillis() - start;
            history.setStatus("FAILED");
            history.setEndTime(Instant.now());
            history.setExecutionDuration(durationMs);
            history.setErrorMessage(e.getMessage());
            historyRepository.save(history);

            log.warn("Failed taskId={} after {}ms on thread={}: {}", taskId, durationMs, thread, e.getMessage());
            return ExecutionResult.builder()
                    .taskId(taskId)
                    .status("FAILED")
                    .durationMs(durationMs)
                    .error(e.getMessage())
                    .build();
        }
    }
}
