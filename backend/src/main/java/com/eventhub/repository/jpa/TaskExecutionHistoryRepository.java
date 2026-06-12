package com.eventhub.repository.jpa;

import com.eventhub.entity.TaskExecutionHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskExecutionHistoryRepository extends JpaRepository<TaskExecutionHistoryEntity, Long> {

    List<TaskExecutionHistoryEntity> findAllByTaskIdOrderByStartTimeDesc(Long taskId);

    @Query("SELECT COUNT(e) FROM TaskExecutionHistoryEntity e")
    long countTotal();

    @Query("SELECT COUNT(e) FROM TaskExecutionHistoryEntity e WHERE e.status = 'SUCCESS'")
    long countSuccess();

    @Query("SELECT COUNT(e) FROM TaskExecutionHistoryEntity e WHERE e.status = 'FAILED'")
    long countFailed();
}
