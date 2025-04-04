package ma.student.task.management.mapper;

import ma.student.task.management.config.MapperConfig;
import ma.student.task.management.dto.user.UserRegistrationRequestDto;
import ma.student.task.management.dto.user.UserResponseDto;
import ma.student.task.management.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toDto(User user);

    User toModel(UserRegistrationRequestDto userRegistrationRequestDto);
}
