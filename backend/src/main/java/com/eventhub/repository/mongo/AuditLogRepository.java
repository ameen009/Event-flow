package com.eventhub.repository.mongo;

import com.eventhub.entity.AuditLogDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends MongoRepository<AuditLogDocument, String> {

    List<AuditLogDocument> findByTaskIdOrderByTimestampDesc(Long taskId);
}
