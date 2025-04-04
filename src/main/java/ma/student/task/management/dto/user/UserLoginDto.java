package ma.student.task.management.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginDto {
    @Email
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
