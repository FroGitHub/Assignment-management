package ma.student.task.management.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;

import ma.student.task.management.TestUtil;
import ma.student.task.management.dto.project.ProjectCreateRequestDto;
import ma.student.task.management.dto.project.ProjectDto;
import ma.student.task.management.exception.EntityNotFoundException;
import ma.student.task.management.mapper.ProjectMapper;
import ma.student.task.management.model.Project;
import ma.student.task.management.repository.ProjectRepository;
import ma.student.task.management.service.impl.ProjectServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectMapper projectMapper;

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Test
    @DisplayName("Create project")
    void createProjectTest_createProject_ok() {
        // Given
        ProjectCreateRequestDto requestDto = TestUtil.getProjectCreateRequestDto();
        Project project = TestUtil.getProject();
        ProjectDto expectedDto = TestUtil.getProjectDto();

        when(projectMapper.toModel(requestDto)).thenReturn(project);
        when(projectRepository.save(project)).thenReturn(project);
        when(projectMapper.toDto(project)).thenReturn(expectedDto);

        // When
        ProjectDto actualDto = projectService.createProject(requestDto);

        // Then
        assertEquals(expectedDto, actualDto);
        verify(projectMapper).toModel(requestDto);
        verify(projectRepository).save(project);
        verify(projectMapper).toDto(project);
    }

    @Test
    @DisplayName("Update project should throw exception when project not found")
    void updateProjectTest_updateProject_notOk() {
        // Given
        Long projectId = 1L;
        ProjectCreateRequestDto requestDto = TestUtil.getProjectCreateRequestDto();

        when(projectRepository.existsById(projectId)).thenReturn(false);

        // When & Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> projectService.updateProject(projectId, requestDto));
        assertEquals("There is no project with id: " + projectId, exception.getMessage());

        verify(projectRepository).existsById(projectId);
        verify(projectRepository, never()).save(any());
    }
}
