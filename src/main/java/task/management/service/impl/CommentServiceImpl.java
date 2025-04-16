package task.management.service.impl;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import task.management.dto.comment.CommentCreateRequestDto;
import task.management.dto.comment.CommentDto;
import task.management.exception.EntityNotFoundException;
import task.management.mapper.CommentMapper;
import task.management.model.Comment;
import task.management.model.Task;
import task.management.model.User;
import task.management.repository.CommentRepository;
import task.management.repository.TaskRepository;
import task.management.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentDto createComment(Authentication authentication,
                                    CommentCreateRequestDto requestDto) {
        User user = (User) authentication.getPrincipal();

        Task task = taskRepository.findById(requestDto.getTaskId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "There is no task with id: " + requestDto.getTaskId()));

        Comment comment = new Comment();
        comment.setUser(user);
        task.addComment(comment);
        comment.setText(requestDto.getText());
        comment.setTimestamp(LocalDateTime.now());

        taskRepository.flush();
        return commentMapper.toDto(comment);
    }

    public Page<CommentDto> getCommentsByTaskId(Pageable pageable, Long id) {
        if (!taskRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    "There is no task with id: " + id);
        }
        return commentRepository.findByTaskId(pageable, id).map(commentMapper::toDto);
    }

}
