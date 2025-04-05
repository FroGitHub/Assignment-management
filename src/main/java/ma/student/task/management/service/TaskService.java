package ma.student.task.management.service;

import ma.student.task.management.dto.task.TaskCreateRequestDto;
import ma.student.task.management.dto.task.TaskDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface TaskService {

    Page<TaskDto> getTasks(Pageable pageable);

    TaskDto createTask(Authentication authentication,
                       TaskCreateRequestDto createRequestDto);

    TaskDto getTask(Long id);

    TaskDto updateTask(Authentication authentication,
                       TaskCreateRequestDto createRequestDto, Long id);

    void deleteTask(Long id, Authentication authentication);
}
