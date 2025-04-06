package ma.student.task.management.dto.user;

import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.Data;
import ma.student.task.management.model.Role;

@Data
public class UserUpdateRoleRequestDto {
    @NotNull
    private Set<Role.RoleName> roles;
}

