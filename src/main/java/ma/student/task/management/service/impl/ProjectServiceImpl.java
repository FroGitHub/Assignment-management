package ma.student.task.management.service.impl;

import lombok.RequiredArgsConstructor;
import ma.student.task.management.dto.project.ProjectCreateRequestDto;
import ma.student.task.management.dto.project.ProjectDto;
import ma.student.task.management.exception.EntityNotFoundException;
import ma.student.task.management.mapper.ProjectMapper;
import ma.student.task.management.model.Project;
import ma.student.task.management.repository.ProjectRepository;
import ma.student.task.management.service.ProjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Override
    public Page<ProjectDto> getProjects(Pageable pageable) {
        return projectRepository.findAll(pageable)
                .map(projectMapper::toDto);
    }

    @Override
    public ProjectDto createProject(ProjectCreateRequestDto createRequestDto) {
        Project newProject = projectMapper.toModel(createRequestDto);
        return projectMapper.toDto(
                projectRepository.save(newProject));
    }

    @Override
    public ProjectDto updateProject(Long id,
                                    ProjectCreateRequestDto createRequestDto) {
        if (!projectRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    "There is no project with id: " + id);
        }

        Project project = projectRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("There is no project with id: " + id));

        projectMapper.updateProject(project, createRequestDto);

        return projectMapper.toDto(projectRepository.save(project));
    }

    @Override
    public ProjectDto getProject(Long id) {
        return projectMapper.toDto(projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "There is no project with id: " + id)));
    }

    @Override
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}
