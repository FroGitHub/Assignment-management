package task.management.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import task.management.config.MapperConfig;
import task.management.dto.task.TaskCreateRequestDto;
import task.management.dto.task.TaskDto;
import task.management.model.Task;

@Mapper(config = MapperConfig.class)
public interface TaskMapper {

    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "assignee.id", target = "assigneeId")
    TaskDto toDto(Task task);

    @Mapping(target = "project", ignore = true)
    @Mapping(target = "assignee", ignore = true)
    Task toModel(TaskDto taskDto);

    @Mapping(target = "assignee", ignore = true)
    @Mapping(target = "project", ignore = true)
    Task toModel(TaskCreateRequestDto taskCreateRequestDto);

    @Mapping(target = "assignee", ignore = true)
    @Mapping(target = "project", ignore = true)
    void updateTask(@MappingTarget Task task,
                    TaskCreateRequestDto createRequestDto);
}
