package ma.student.task.management.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.student.task.management.dto.task.TaskCreateRequestDto;
import ma.student.task.management.dto.task.TaskDto;
import ma.student.task.management.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public Page<TaskDto> getTasks(Pageable pageable) {
        return taskService.getTasks(pageable);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDto createTask(Authentication authentication,
                              @RequestBody @Valid TaskCreateRequestDto createRequestDto) {
        return taskService.createTask(authentication, createRequestDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public TaskDto updateTask(Authentication authentication,
                              @PathVariable Long id,
                              @RequestBody @Valid TaskCreateRequestDto createRequestDto) {
        return taskService.updateTask(authentication, createRequestDto, id);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public TaskDto getTask(@PathVariable Long id) {
        return taskService.getTask(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id,
                              Authentication authentication) {
        taskService.deleteTask(id, authentication);
    }
}
