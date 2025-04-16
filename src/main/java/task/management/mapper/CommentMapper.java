package task.management.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import task.management.config.MapperConfig;
import task.management.dto.comment.CommentDto;
import task.management.model.Comment;

@Mapper(config = MapperConfig.class)
public interface CommentMapper {

    @Mapping(target = "taskId", source = "task.id")
    @Mapping(target = "userId", source = "user.id")
    CommentDto toDto(Comment comment);
}
