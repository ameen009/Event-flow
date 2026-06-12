package com.eventhub.service;

import com.eventhub.entity.AuditLogDocument;
import com.eventhub.repository.jpa.TaskRepository;
import com.eventhub.repository.mongo.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final TaskRepository taskRepository;

    public void log(String action, Long taskId, String taskName, Map<String, Object> details) {
        AuditLogDocument doc = AuditLogDocument.builder()
                .action(action)
                .taskId(taskId)
                .taskName(taskName)
                .performedBy("system")
                .timestamp(Instant.now())
                .details(details)
                .build();
        auditLogRepository.save(doc);
        log.debug("Audit log saved: action={} taskId={}", action, taskId);
    }

    public List<AuditLogDocument> getLogsForTask(Long taskId) {
        return auditLogRepository.findByTaskIdOrderByTimestampDesc(taskId);
    }
}
