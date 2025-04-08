package ma.student.task.management.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.student.task.management.dto.comment.CommentCreateRequestDto;
import ma.student.task.management.dto.comment.CommentDto;
import ma.student.task.management.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public CommentDto addComment(Authentication authentication,
                                 @RequestBody @Valid CommentCreateRequestDto requestDto) {
        return commentService.createComment(authentication, requestDto);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public Page<CommentDto> getCommentsByTaskId(Pageable pageable, Long taskId) {
        return commentService.getCommentsByTaskId(pageable, taskId);
    }

}
