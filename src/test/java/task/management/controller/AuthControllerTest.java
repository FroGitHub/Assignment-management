package task.management.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import task.management.dto.user.UserLoginDto;
import task.management.dto.user.UserRegistrationRequestDto;
import task.management.dto.user.UserResponseDto;
import task.management.dto.user.UserResponseLoginDto;
import task.management.security.AuthenticationService;
import task.management.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private UserService userService;

    @Mock
    private AuthenticationService authenticationService;

    @Test
    @DisplayName("Should register user and return UserResponseDto")
    void register_ValidRequest_ReturnsUserResponseDto() {
        // Given
        UserRegistrationRequestDto requestDto = new UserRegistrationRequestDto();
        requestDto.setEmail("test@example.com");
        requestDto.setUsername("tester");
        requestDto.setPassword("securePassword123");
        requestDto.setRepeatPassword("securePassword123");

        UserResponseDto expectedResponse = new UserResponseDto();
        expectedResponse.setEmail(requestDto.getEmail());
        expectedResponse.setUsername(requestDto.getUsername());

        when(userService.register(requestDto)).thenReturn(expectedResponse);

        // When
        UserResponseDto actual = authController.register(requestDto);

        // Then
        assertEquals(expectedResponse, actual);
        verify(userService).register(requestDto);
    }

    @Test
    @DisplayName("Should login user and return UserResponseLoginDto")
    void login_ValidCredentials_ReturnsJwt() {
        // Given
        UserLoginDto loginDto = new UserLoginDto();
        loginDto.setUsername("test");
        loginDto.setPassword("securePassword123");

        UserResponseLoginDto expected = new UserResponseLoginDto("token-123");

        when(authenticationService.authenticate(loginDto)).thenReturn(expected);

        // When
        UserResponseLoginDto actual = authController.login(loginDto);

        // Then
        assertEquals(expected, actual);
        verify(authenticationService).authenticate(loginDto);
    }
}
