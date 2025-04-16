package task.management.mapper;

import task.management.config.MapperConfig;
import task.management.dto.label.LabelCreateRequestDto;
import task.management.dto.label.LabelDto;
import task.management.model.Label;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface LabelMapper {

    LabelDto toDto(Label label);

    @Mapping(target = "id", source = "taskId")
    Label toModel(LabelCreateRequestDto createRequestDto);

    @Mapping(target = "id", ignore = true)
    void updateLabel(@MappingTarget Label label,
                     LabelCreateRequestDto createRequestDto);

}
