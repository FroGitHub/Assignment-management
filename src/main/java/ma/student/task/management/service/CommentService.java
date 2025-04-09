package ma.student.task.management.service;

import ma.student.task.management.dto.comment.CommentCreateRequestDto;
import ma.student.task.management.dto.comment.CommentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface CommentService {
    CommentDto createComment(Authentication authentication, CommentCreateRequestDto requestDto);

    Page<CommentDto> getCommentsByTaskId(Pageable pageable, Long taskId);
}
