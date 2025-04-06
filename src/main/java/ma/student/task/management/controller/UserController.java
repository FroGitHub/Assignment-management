package ma.student.task.management.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.student.task.management.dto.user.UserResponseDto;
import ma.student.task.management.dto.user.UserUpdateRequestDto;
import ma.student.task.management.dto.user.UserUpdateRoleRequestDto;
import ma.student.task.management.dto.user.UserWithRoleDto;
import ma.student.task.management.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public UserWithRoleDto getUser(Authentication authentication) {
        return userService.getMyUserInfo(authentication);
    }

    @PatchMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public UserResponseDto updateUser(
            Authentication authentication,
            @RequestBody @Valid UserUpdateRequestDto requestDto) {
        return userService.updateUser(authentication, requestDto);
    }

    @PatchMapping("/{id}/role")
    @PreAuthorize("hasRole('USER')")
    public UserWithRoleDto updateUserRole(@PathVariable Long id,
            @RequestBody @Valid UserUpdateRoleRequestDto requestDto) {
        return userService.updateUserRole(id, requestDto);
    }

}
