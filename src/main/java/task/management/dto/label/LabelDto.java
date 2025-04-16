package task.management.dto.label;

import lombok.Data;
import task.management.model.Label;

@Data
public class LabelDto {
    private Long id;
    private String name;
    private Label.Color color;
}
