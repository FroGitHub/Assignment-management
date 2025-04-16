package task.management.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import task.management.config.MapperConfig;
import task.management.dto.project.ProjectCreateRequestDto;
import task.management.dto.project.ProjectDto;
import task.management.model.Project;
import task.management.model.Task;

@Mapper(config = MapperConfig.class)
public interface ProjectMapper {

    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(target = "taskIds", ignore = true)
    ProjectDto toDto(Project project);

    @AfterMapping
    default void tasksToIds(
            Project project,
            @MappingTarget ProjectDto projectDto) {
        projectDto.taskIds().addAll(
                project.getTasks().stream().map(Task::getId).toList()
        );
    }

    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    Project toModel(ProjectCreateRequestDto dto);

    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "tasks", ignore = true)
    Project toModel(ProjectDto dto);

    @AfterMapping
    default void taskIdsToTask(
            @MappingTarget Project project,
            ProjectDto projectDto) {

        projectDto.taskIds().stream()
                .map(Task::new)
                .forEach(project::addTask);

    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    void updateProject(@MappingTarget Project project,
                       ProjectCreateRequestDto createRequestDto);
}
