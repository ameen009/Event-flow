package com.eventhub.mapper;

import com.eventhub.dto.ExecutionHistoryResponse;
import com.eventhub.dto.TaskRequest;
import com.eventhub.dto.TaskResponse;
import com.eventhub.entity.TaskEntity;
import com.eventhub.entity.TaskExecutionHistoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    TaskEntity toEntity(TaskRequest request);

    TaskResponse toResponse(TaskEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    void updateEntity(TaskRequest request, @MappingTarget TaskEntity entity);

    ExecutionHistoryResponse toExecutionResponse(TaskExecutionHistoryEntity entity);
}
