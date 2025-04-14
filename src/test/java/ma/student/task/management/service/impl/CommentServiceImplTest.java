package ma.student.task.management.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;
import ma.student.task.management.dto.comment.CommentCreateRequestDto;
import ma.student.task.management.dto.comment.CommentDto;
import ma.student.task.management.exception.EntityNotFoundException;
import ma.student.task.management.mapper.CommentMapper;
import ma.student.task.management.model.Comment;
import ma.student.task.management.model.Task;
import ma.student.task.management.model.User;
import ma.student.task.management.repository.CommentRepository;
import ma.student.task.management.repository.TaskRepository;
import ma.student.task.management.service.impl.CommentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;

class CommentServiceImplTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private CommentMapper commentMapper;
    @Mock
    private Authentication authentication;

    @InjectMocks
    private CommentServiceImpl commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Create comment - success")
    void createCommentTest_createComment_ok() {
        // Given
        CommentCreateRequestDto requestDto = new CommentCreateRequestDto();
        requestDto.setTaskId(1L);
        requestDto.setText("Test comment");

        Task task = new Task();
        Comment comment = new Comment();
        User user = new User();
        CommentDto expectedDto = new CommentDto(1L, 1L, 1L, "Test comment", LocalDateTime.now());

        when(authentication.getPrincipal()).thenReturn(user);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(commentMapper.toDto(any(Comment.class))).thenReturn(expectedDto);

        // When
        CommentDto actualDto = commentService.createComment(authentication, requestDto);

        // Then
        assertEquals(expectedDto, actualDto);
        verify(taskRepository).findById(1L);
        verify(taskRepository).flush();
        verify(commentMapper).toDto(any(Comment.class));
    }

    @Test
    @DisplayName("Create comment - task not found")
    void createCommentTest_createComment_notOk() {
        // Given
        CommentCreateRequestDto requestDto = new CommentCreateRequestDto();
        requestDto.setTaskId(1L);
        requestDto.setText("Test");

        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> commentService.createComment(authentication, requestDto));
        assertEquals("There is no task with id: 1", exception.getMessage());
    }

    @Test
    @DisplayName("Get comments by task id - success")
    void getCommentsByTaskIdTest_getCommentsByTaskId_ok() {
        // Given
        Long taskId = 1L;
        PageRequest pageable = PageRequest.of(0, 10);
        Comment comment = new Comment();
        CommentDto dto = new CommentDto(1L, 1L, 1L, "Test", LocalDateTime.now());

        when(taskRepository.existsById(taskId)).thenReturn(true);
        when(commentRepository.findByTaskId(pageable, taskId))
                .thenReturn(new PageImpl<>(List.of(comment)));
        when(commentMapper.toDto(comment)).thenReturn(dto);

        // When
        Page<CommentDto> result = commentService.getCommentsByTaskId(pageable, taskId);

        // Then
        assertEquals(1, result.getTotalElements());
        assertEquals(dto, result.getContent().get(0));
        verify(taskRepository).existsById(taskId);
        verify(commentRepository).findByTaskId(pageable, taskId);
    }

    @Test
    @DisplayName("Get comments by task id - task not found")
    void getCommentsByTaskIdTest_getCommentsByTaskIdTest_notOk() {
        // Given
        Long taskId = 1L;
        PageRequest pageable = PageRequest.of(0, 10);

        when(taskRepository.existsById(taskId)).thenReturn(false);

        // When & Then
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> commentService.getCommentsByTaskId(pageable, taskId));
        assertEquals("There is no task with id: 1", exception.getMessage());
    }
}
