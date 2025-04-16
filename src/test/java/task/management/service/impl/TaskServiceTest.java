package task.management.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import task.management.util.TestUtil;
import task.management.dto.task.TaskCreateRequestDto;
import task.management.dto.task.TaskDto;
import task.management.mapper.TaskMapper;
import task.management.model.Project;
import task.management.model.Task;
import task.management.model.User;
import task.management.repository.ProjectRepository;
import task.management.repository.TaskRepository;
import task.management.repository.UserRepository;
import task.management.service.EmailService;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private TaskMapper taskMapper;
    @Mock
    private EmailService emailService;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    @DisplayName("Create task successfully")
    void createTaskTest_createTask_ok() {
        // Given
        TaskCreateRequestDto requestDto = TestUtil.getTaskCreateRequestDto();
        Project project = TestUtil.getProject();
        User user = TestUtil.getUser();
        Task task = TestUtil.getTask();
        Task savedTask = TestUtil.getTask();
        TaskDto expectedDto = TestUtil.getTaskDto();

        when(projectRepository.findById(requestDto.getProjectId())).thenReturn(Optional.of(project));
        when(userRepository.findById(requestDto.getAssigneeId())).thenReturn(Optional.of(user));
        when(taskMapper.toModel(requestDto)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(savedTask);
        when(taskMapper.toDto(savedTask)).thenReturn(expectedDto);

        // When
        TaskDto actualDto = taskService.createTask(null, requestDto);

        // Then
        assertEquals(expectedDto, actualDto);
        verify(emailService).sendEmail(user.getEmail(), "You got new task!" + task);
    }

    @Test
    @DisplayName("Get all tasks - should return paged list")
    void getTasksTest_getTasks_ok() {
        // Given
        Task task = TestUtil.getTask();
        TaskDto taskDto = TestUtil.getTaskDto();
        Pageable pageable = PageRequest.of(0, 10);
        Page<Task> taskPage = new PageImpl<>(List.of(task));

        when(taskRepository.findAll(pageable)).thenReturn(taskPage);
        when(taskMapper.toDto(task)).thenReturn(taskDto);

        // When
        Page<TaskDto> result = taskService.getTasks(pageable);

        // Then
        assertEquals(1, result.getTotalElements());
        assertEquals(taskDto, result.getContent().get(0));
        verify(taskRepository).findAll(pageable);
        verify(taskMapper).toDto(task);
    }

    @Test
    @DisplayName("Get all tasks - should return empty page when no tasks")
    void getTasksTest_getTasks_notOk() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Page<Task> emptyPage = new PageImpl<>(List.of());

        when(taskRepository.findAll(pageable)).thenReturn(emptyPage);

        // When
        Page<TaskDto> result = taskService.getTasks(pageable);

        // Then
        assertTrue(result.isEmpty());
        verify(taskRepository).findAll(pageable);
        verifyNoInteractions(taskMapper);
    }


}
