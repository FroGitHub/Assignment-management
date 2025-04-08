package ma.student.task.management.mapper;

import ma.student.task.management.config.MapperConfig;
import ma.student.task.management.dto.comment.CommentDto;
import ma.student.task.management.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface CommentMapper {

    @Mapping(target = "taskId", source = "task.id")
    @Mapping(target = "userId", source = "user.id")
    CommentDto toDto(Comment comment);
}
