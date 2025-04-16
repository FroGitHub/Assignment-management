package task.management.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import task.management.dto.comment.CommentCreateRequestDto;
import task.management.dto.comment.CommentDto;

public interface CommentService {
    CommentDto createComment(Authentication authentication, CommentCreateRequestDto requestDto);

    Page<CommentDto> getCommentsByTaskId(Pageable pageable, Long taskId);
}
