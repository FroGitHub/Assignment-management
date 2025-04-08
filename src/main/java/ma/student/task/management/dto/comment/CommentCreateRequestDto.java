package ma.student.task.management.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentCreateRequestDto {
    @NotNull
    private Long taskId;
    @NotBlank
    private String text;
}
