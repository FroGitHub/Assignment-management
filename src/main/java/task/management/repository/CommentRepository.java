package task.management.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import task.management.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByTaskId(Pageable pageable, Long id);
}
