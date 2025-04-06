package ma.student.task.management.service;

import ma.student.task.management.dto.user.UserRegistrationRequestDto;
import ma.student.task.management.dto.user.UserResponseDto;
import ma.student.task.management.dto.user.UserUpdateRequestDto;
import ma.student.task.management.dto.user.UserUpdateRoleRequestDto;
import ma.student.task.management.dto.user.UserWithRoleDto;
import ma.student.task.management.exception.RegistrationException;
import org.springframework.security.core.Authentication;

public interface UserService {

    public UserResponseDto register(UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException;

    UserWithRoleDto getMyUserInfo(Authentication authentication);

    UserResponseDto updateUser(
            Authentication authentication,
            UserUpdateRequestDto requestDto);

    UserWithRoleDto updateUserRole(Long id, UserUpdateRoleRequestDto requestDto);
}
