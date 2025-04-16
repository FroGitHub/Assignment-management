package task.management.mapper;

import task.management.config.MapperConfig;
import task.management.dto.attachment.AttachmentDto;
import task.management.model.Attachment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface AttachmentMapper {

    @Mapping(target = "taskId", source = "task.id")
    AttachmentDto toDto(Attachment attachment);
}
