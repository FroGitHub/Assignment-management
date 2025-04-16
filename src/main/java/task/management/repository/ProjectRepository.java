package task.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import task.management.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
