package ma.student.task.management.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.student.task.management.dto.project.ProjectCreateRequestDto;
import ma.student.task.management.dto.project.ProjectDto;
import ma.student.task.management.service.ProjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public Page<ProjectDto> getProjects(Pageable pageable) {
        return projectService.getProjects(pageable);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectDto createProject(
            @RequestBody @Valid ProjectCreateRequestDto createRequestDto) {
        return projectService.createProject(createRequestDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ProjectDto updateProject(
            @PathVariable Long id,
            @RequestBody @Valid ProjectCreateRequestDto createRequestDto) {
        return projectService.updateProject(id, createRequestDto);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ProjectDto getProject(
            @PathVariable Long id) {
        return projectService.getProject(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(
            @PathVariable Long id) {
        projectService.deleteProject(id);
    }

}
