package com.eventhub.mapper;

import com.eventhub.dto.ExecutionHistoryResponse;
import com.eventhub.dto.TaskRequest;
import com.eventhub.dto.TaskResponse;
import com.eventhub.entity.TaskEntity;
import com.eventhub.entity.TaskExecutionHistoryEntity;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-11T11:18:42+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (SAP SE)"
)
@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public TaskEntity toEntity(TaskRequest request) {
        if ( request == null ) {
            return null;
        }

        TaskEntity.TaskEntityBuilder taskEntity = TaskEntity.builder();

        taskEntity.name( request.getName() );
        taskEntity.cronExpression( request.getCronExpression() );
        taskEntity.type( request.getType() );
        Map<String, Object> map = request.getPayload();
        if ( map != null ) {
            taskEntity.payload( new LinkedHashMap<String, Object>( map ) );
        }
        taskEntity.status( request.getStatus() );

        return taskEntity.build();
    }

    @Override
    public TaskResponse toResponse(TaskEntity entity) {
        if ( entity == null ) {
            return null;
        }

        TaskResponse taskResponse = new TaskResponse();

        taskResponse.setId( entity.getId() );
        taskResponse.setName( entity.getName() );
        taskResponse.setCronExpression( entity.getCronExpression() );
        taskResponse.setType( entity.getType() );
        Map<String, Object> map = entity.getPayload();
        if ( map != null ) {
            taskResponse.setPayload( new LinkedHashMap<String, Object>( map ) );
        }
        taskResponse.setStatus( entity.getStatus() );
        taskResponse.setCreatedAt( entity.getCreatedAt() );
        taskResponse.setUpdatedAt( entity.getUpdatedAt() );
        taskResponse.setCreatedBy( entity.getCreatedBy() );

        return taskResponse;
    }

    @Override
    public void updateEntity(TaskRequest request, TaskEntity entity) {
        if ( request == null ) {
            return;
        }

        entity.setName( request.getName() );
        entity.setCronExpression( request.getCronExpression() );
        entity.setType( request.getType() );
        if ( entity.getPayload() != null ) {
            Map<String, Object> map = request.getPayload();
            if ( map != null ) {
                entity.getPayload().clear();
                entity.getPayload().putAll( map );
            }
            else {
                entity.setPayload( null );
            }
        }
        else {
            Map<String, Object> map = request.getPayload();
            if ( map != null ) {
                entity.setPayload( new LinkedHashMap<String, Object>( map ) );
            }
        }
        entity.setStatus( request.getStatus() );
    }

    @Override
    public ExecutionHistoryResponse toExecutionResponse(TaskExecutionHistoryEntity entity) {
        if ( entity == null ) {
            return null;
        }

        ExecutionHistoryResponse executionHistoryResponse = new ExecutionHistoryResponse();

        executionHistoryResponse.setId( entity.getId() );
        executionHistoryResponse.setTaskId( entity.getTaskId() );
        executionHistoryResponse.setStatus( entity.getStatus() );
        executionHistoryResponse.setStartTime( entity.getStartTime() );
        executionHistoryResponse.setEndTime( entity.getEndTime() );
        executionHistoryResponse.setExecutionDuration( entity.getExecutionDuration() );
        executionHistoryResponse.setErrorMessage( entity.getErrorMessage() );

        return executionHistoryResponse;
    }
}
