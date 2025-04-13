package ma.student.task.management.service.impl;

import lombok.RequiredArgsConstructor;
import ma.student.task.management.dto.task.TaskCreateRequestDto;
import ma.student.task.management.dto.task.TaskDto;
import ma.student.task.management.exception.EntityNotFoundException;
import ma.student.task.management.mapper.TaskMapper;
import ma.student.task.management.model.Project;
import ma.student.task.management.model.Task;
import ma.student.task.management.model.User;
import ma.student.task.management.repository.ProjectRepository;
import ma.student.task.management.repository.TaskRepository;
import ma.student.task.management.repository.UserRepository;
import ma.student.task.management.service.EmailService;
import ma.student.task.management.service.TaskService;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final EmailService emailService;

    @Override
    public Page<TaskDto> getTasks(Pageable pageable) {
        return taskRepository.findAll(pageable)
                .map(taskMapper::toDto);
    }

    @Override
    public TaskDto createTask(Authentication authentication,
                              TaskCreateRequestDto createRequestDto) {

        Pair<User, Project> userAndProject = getExistingUserAndProject(
                createRequestDto.getAssigneeId(),
                createRequestDto.getProjectId());

        Task task = taskMapper.toModel(createRequestDto);

        task.setProject(userAndProject.getRight());
        task.setAssignee(userAndProject.getLeft());

        emailService.sendEmail(task.getAssignee().getEmail(),
                "You got new task!" + task);

        return taskMapper.toDto(taskRepository.save(task));
    }

    @Override
    public TaskDto getTask(Long id) {
        return taskMapper.toDto(taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "There is no task with id: " + id)));
    }

    @Override
    public TaskDto updateTask(Authentication authentication,
                              TaskCreateRequestDto createRequestDto,
                              Long id) {

        Pair<User, Project> userAndProject = getExistingUserAndProject(
                createRequestDto.getAssigneeId(),
                createRequestDto.getProjectId());

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "There is no task with id: " + id));

        task.setProject(userAndProject.getRight());
        task.setAssignee(userAndProject.getLeft());

        taskMapper.updateTask(task, createRequestDto);

        emailService.sendEmail(task.getAssignee().getEmail(),
                "Your task was updated!" + task);

        return taskMapper.toDto(taskRepository.save(task));
    }

    @Override
    public void deleteTask(Long id, Authentication authentication) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "There is no task with id: " + id));
        emailService.sendEmail(task.getAssignee().getEmail(),
                "Your task was deleted!" + task);
        taskRepository.deleteById(id);
    }

    private Pair<User, Project> getExistingUserAndProject(Long userId, Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("No project with id: " + projectId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("No user with id: " + userId));
        return Pair.of(user, project);
    }
}
