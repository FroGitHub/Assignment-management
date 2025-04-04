package ma.student.task.management.repository;

import java.util.Optional;
import ma.student.task.management.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = "roles")
    Optional<User> findByUsername(String username);

    boolean existsByEmail(String email);
}
