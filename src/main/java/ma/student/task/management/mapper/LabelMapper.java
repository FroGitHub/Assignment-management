package ma.student.task.management.mapper;

import ma.student.task.management.config.MapperConfig;
import ma.student.task.management.dto.label.LabelCreateRequestDto;
import ma.student.task.management.dto.label.LabelDto;
import ma.student.task.management.model.Label;
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
