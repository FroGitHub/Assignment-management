package task.management.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import task.management.dto.project.ProjectCreateRequestDto;
import task.management.dto.project.ProjectDto;

public interface ProjectService {
    Page<ProjectDto> getProjects(Pageable pageable);

    ProjectDto createProject(ProjectCreateRequestDto createRequestDto);

    ProjectDto updateProject(Long id, ProjectCreateRequestDto createRequestDto);

    ProjectDto getProject(Long id);

    void deleteProject(Long id);
}
