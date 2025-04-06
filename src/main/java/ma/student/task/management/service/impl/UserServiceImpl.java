package ma.student.task.management.service.impl;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import ma.student.task.management.dto.user.UserRegistrationRequestDto;
import ma.student.task.management.dto.user.UserResponseDto;
import ma.student.task.management.dto.user.UserUpdateRequestDto;
import ma.student.task.management.dto.user.UserUpdateRoleRequestDto;
import ma.student.task.management.dto.user.UserWithRoleDto;
import ma.student.task.management.exception.EntityNotFoundException;
import ma.student.task.management.exception.RegistrationException;
import ma.student.task.management.mapper.UserMapper;
import ma.student.task.management.model.Role;
import ma.student.task.management.model.User;
import ma.student.task.management.repository.RoleRepository;
import ma.student.task.management.repository.UserRepository;
import ma.student.task.management.service.UserService;
import org.springframework.security.core.Authentication;
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

    @Override
    public UserWithRoleDto getMyUserInfo(Authentication authentication) {
        return userMapper.toDtoWithRole((User) authentication.getPrincipal());
    }

    @Override
    public UserResponseDto updateUser(
            Authentication authentication,
            UserUpdateRequestDto requestDto) {
        User user = (User) authentication.getPrincipal();

        userMapper.updateUser(user, requestDto);
        if (Objects.equals(user.getPassword(), requestDto.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserWithRoleDto updateUserRole(Long id, UserUpdateRoleRequestDto requestDto) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("There is no user with id: " + id));

        Set<Role> roles = requestDto.getRoles().stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new EntityNotFoundException(
                                "There is no role with name: " + roleName)))
                .collect(Collectors.toSet());
        user.setRoles(roles);
        return userMapper.toDtoWithRole(user);
    }

}
