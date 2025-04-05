package ma.student.task.management.mapper;

import ma.student.task.management.config.MapperConfig;
import ma.student.task.management.dto.task.TaskCreateRequestDto;
import ma.student.task.management.dto.task.TaskDto;
import ma.student.task.management.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

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
