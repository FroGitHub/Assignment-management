package ma.student.task.management.dto.label;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ma.student.task.management.model.Label;

@Data
public class LabelCreateRequestDto {
    @NotNull
    private Long taskId;
    @NotBlank
    @Size(min = 4, message = "label name should be longer then 4")
    private String name;
    @NotNull
    private Label.Color color;

}
