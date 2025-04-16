package task.management.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import task.management.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(Role.RoleName roleName);
}
