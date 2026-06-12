package com.eventhub.service;

import com.eventhub.dto.TaskRequest;
import com.eventhub.dto.TaskResponse;
import com.eventhub.entity.TaskEntity;
import com.eventhub.exception.ResourceNotFoundException;
import com.eventhub.mapper.TaskMapper;
import com.eventhub.repository.jpa.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final AuditLogService auditLogService;

    @Transactional
    public TaskResponse createTask(TaskRequest request) {
        validateCronExpression(request.getCronExpression());
        TaskEntity entity = taskMapper.toEntity(request);
        TaskEntity saved = taskRepository.save(entity);
        log.info("Created task id={} name={}", saved.getId(), saved.getName());
        auditLogService.log("CREATE", saved.getId(), saved.getName(),
                Map.of("cronExpression", saved.getCronExpression(), "type", saved.getType()));
        return taskMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(taskMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public TaskResponse getTaskById(Long id) {
        return taskMapper.toResponse(findOrThrow(id));
    }

    @Transactional
    public TaskResponse updateTask(Long id, TaskRequest request) {
        validateCronExpression(request.getCronExpression());
        TaskEntity entity = findOrThrow(id);
        taskMapper.updateEntity(request, entity);
        TaskEntity saved = taskRepository.save(entity);
        log.info("Updated task id={}", saved.getId());
        auditLogService.log("UPDATE", saved.getId(), saved.getName(),
                Map.of("cronExpression", saved.getCronExpression(), "status", saved.getStatus()));
        return taskMapper.toResponse(saved);
    }

    @Transactional
    public void deleteTask(Long id) {
        TaskEntity entity = findOrThrow(id);
        taskRepository.delete(entity);
        log.info("Deleted task id={}", id);
        auditLogService.log("DELETE", id, entity.getName(), Map.of());
    }

    private TaskEntity findOrThrow(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
    }

    private void validateCronExpression(String cron) {
        try {
            org.springframework.scheduling.support.CronExpression.parse(cron);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid cron expression: " + cron);
        }
    }
}
