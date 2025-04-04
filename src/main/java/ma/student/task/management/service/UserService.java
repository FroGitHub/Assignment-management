package ma.student.task.management.service;

import ma.student.task.management.dto.user.UserRegistrationRequestDto;
import ma.student.task.management.dto.user.UserResponseDto;
import ma.student.task.management.exception.RegistrationException;

public interface UserService {

    public UserResponseDto register(UserRegistrationRequestDto userRegistrationRequestDto)
            throws RegistrationException;
}
