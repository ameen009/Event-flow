package com.eventhub.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Map;

@Document(collection = "audit_logs")
@Data
@Builder
public class AuditLogDocument {

    @Id
    private String id;

    private String action;        // CREATE, UPDATE, DELETE
    private Long taskId;
    private String taskName;
    private String performedBy;   // username or "system"
    private Instant timestamp;
    private Map<String, Object> details; // flexible — stores old/new values, error info, etc.
}
