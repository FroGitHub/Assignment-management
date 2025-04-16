package task.management.mapper;

import java.util.stream.Collectors;
import task.management.config.MapperConfig;
import task.management.dto.user.UserRegistrationRequestDto;
import task.management.dto.user.UserResponseDto;
import task.management.dto.user.UserUpdateRequestDto;
import task.management.dto.user.UserWithRoleDto;
import task.management.model.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toDto(User user);

    User toModel(UserRegistrationRequestDto userRegistrationRequestDto);

    @Mapping(target = "roleNames", ignore = true)
    UserWithRoleDto toDtoWithRole(User user);

    @AfterMapping
    default void userRolesToString(@MappingTarget UserWithRoleDto userWithRoleDto,
                                   User user) {
        userWithRoleDto.setRoleNames(user.getRoles()
                .stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet()));
    }

    void updateUser(@MappingTarget User user, UserUpdateRequestDto requestDto);
}
