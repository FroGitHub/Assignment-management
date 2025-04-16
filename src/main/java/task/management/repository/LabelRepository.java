package task.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import task.management.model.Label;

public interface LabelRepository extends JpaRepository<Label, Long> {
}
