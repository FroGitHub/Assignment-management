package task.management.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import task.management.config.MapperConfig;
import task.management.dto.attachment.AttachmentDto;
import task.management.model.Attachment;

@Mapper(config = MapperConfig.class)
public interface AttachmentMapper {

    @Mapping(target = "taskId", source = "task.id")
    AttachmentDto toDto(Attachment attachment);
}
