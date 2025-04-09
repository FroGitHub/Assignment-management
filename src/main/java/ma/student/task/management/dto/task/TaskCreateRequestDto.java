package ma.student.task.management.dto.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Data;
import ma.student.task.management.model.Status;
import ma.student.task.management.model.Task;

@Data
public class TaskCreateRequestDto {
    @NotBlank
    @Size(min = 4, message = "Name should be longer than 4")
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private Task.Priority priority;
    @NotNull
    private Status status;
    @NotNull
    private LocalDate dueDate;
    @NotNull
    private Long projectId;
    @NotNull
    private Long assigneeId;
}
