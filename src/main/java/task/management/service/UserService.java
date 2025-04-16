package task.management.service;

import org.springframework.security.core.Authentication;
import task.management.dto.user.UserRegistrationRequestDto;
import task.management.dto.user.UserResponseDto;
import task.management.dto.user.UserUpdateRequestDto;
import task.management.dto.user.UserUpdateRoleRequestDto;
import task.management.dto.user.UserWithRoleDto;
import task.management.exception.RegistrationException;

public interface UserService {

    public UserResponseDto register(UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException;

    UserWithRoleDto getMyUserInfo(Authentication authentication);

    UserResponseDto updateUser(
            Authentication authentication,
            UserUpdateRequestDto requestDto);

    UserWithRoleDto updateUserRole(Long id, UserUpdateRoleRequestDto requestDto);
}
