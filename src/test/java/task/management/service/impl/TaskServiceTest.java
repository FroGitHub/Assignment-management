package task.management.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import task.management.service.TaskService;
import task.management.util.TestUtil;
import task.management.dto.task.TaskCreateRequestDto;
import task.management.dto.task.TaskDto;
import task.management.service.EmailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TaskServiceTest {
    @MockBean
    private EmailService emailService;
    @Autowired
    private TaskService taskService;

    @BeforeAll
    public void mockEmail() {
        doNothing().when(emailService).sendEmail(anyString(), anyString());
    }

    @Test
    @DisplayName("Create task successfully")
    @Sql(scripts = {"classpath:database/user/create-user.sql",
            "classpath:database/project/create-project.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:database/task/delete-task-with-id-1.sql",
            "classpath:database/project/delete-project-with-id-1.sql",
            "classpath:database/user/delete-user-with-id-1.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void createTaskTest_createTask_ok() {
        // Given
        TaskCreateRequestDto requestDto = TestUtil.getTaskCreateRequestDto();
        TaskDto expectedDto = TestUtil.getCreatedTask();

        // When
        TaskDto actualDto = taskService.createTask(null, requestDto);

        // Then
        assertEquals(expectedDto, actualDto);
    }

    @Test
    @DisplayName("Get all tasks - should return paged list")
    @Sql(scripts = {"classpath:database/user/create-user.sql",
            "classpath:database/project/create-project.sql",
            "classpath:database/task/create-task.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:database/task/delete-task-with-id-1.sql",
            "classpath:database/project/delete-project-with-id-1.sql",
            "classpath:database/user/delete-user-with-id-1.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getTasksTest_getTasks_ok() {
        // Given
        TaskDto taskDto = TestUtil.getTaskDto();
        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<TaskDto> result = taskService.getTasks(pageable);

        // Then
        assertEquals(1, result.getTotalElements());
        assertEquals(taskDto, result.getContent().get(0));
    }

    @Test
    @DisplayName("Get all tasks - should return empty page when no tasks")
    void getTasksTest_getTasks_notOk() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);

        // When
        Page<TaskDto> result = taskService.getTasks(pageable);

        // Then
        assertTrue(result.isEmpty());
    }
}
