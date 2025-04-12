package ma.student.task.management.mapper;

import ma.student.task.management.config.MapperConfig;
import ma.student.task.management.dto.attachment.AttachmentDto;
import ma.student.task.management.model.Attachment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface AttachmentMapper {

    @Mapping(target = "taskId", source = "task.id")
    AttachmentDto toDto(Attachment attachment);
}
