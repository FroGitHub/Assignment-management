package ma.student.task.management.service.impl;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import ma.student.task.management.dto.user.UserRegistrationRequestDto;
import ma.student.task.management.dto.user.UserResponseDto;
import ma.student.task.management.exception.EntityNotFoundException;
import ma.student.task.management.exception.RegistrationException;
import ma.student.task.management.mapper.UserMapper;
import ma.student.task.management.model.Role;
import ma.student.task.management.model.User;
import ma.student.task.management.repository.RoleRepository;
import ma.student.task.management.repository.UserRepository;
import ma.student.task.management.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto userRegistrationRequestDto) {
        if (userRepository.existsByEmail(userRegistrationRequestDto.getEmail())) {
            throw new RegistrationException(
                    "User already exists with email: " + userRegistrationRequestDto.getEmail()
            );
        }
        User user = userMapper.toModel(userRegistrationRequestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByName(Role.RoleName.ROLE_USER)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Role is not found: " + Role.RoleName.ROLE_USER)
                );
        user.setRoles(Set.of(role));

        return userMapper.toDto(userRepository.save(user));
    }
}
