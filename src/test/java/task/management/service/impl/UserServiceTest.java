package task.management.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import task.management.dto.user.UserLoginDto;
import task.management.dto.user.UserRegistrationRequestDto;
import task.management.dto.user.UserResponseDto;
import task.management.dto.user.UserResponseLoginDto;
import task.management.security.AuthenticationService;
import task.management.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;


    @Test
    @DisplayName("Should register user and return UserResponseDto")
    @Sql(scripts = "classpath:database/user/delete-users.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/user/delete-user-with-id-1.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void register_validRequest_returnsUserResponseDto() {
        // Given
        UserRegistrationRequestDto requestDto = new UserRegistrationRequestDto();
        requestDto.setEmail("test@example.com");
        requestDto.setUsername("tester");
        requestDto.setPassword("securePassword123");
        requestDto.setRepeatPassword("securePassword123");
        requestDto.setFirstName("user");
        requestDto.setLastName("user");

        // When
        UserResponseDto response = userService.register(requestDto);

        // Then
        assertEquals("test@example.com", response.getEmail());
        assertEquals("tester", response.getUsername());
    }

    @Test
    @DisplayName("Should login user and return JWT token")
    @Sql(scripts = "classpath:database/user/create-user.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/user/delete-user-with-id-1.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void login_validCredentials_returnsJwtToken() {
        // Given
        UserLoginDto loginDto = new UserLoginDto();
        loginDto.setUsername("user");
        loginDto.setPassword("securePassword123");

        // When
        UserResponseLoginDto response = authenticationService.authenticate(loginDto);

        // Then
        assertNotNull(response.token());
        assertTrue(response.token().startsWith("eyJ"));
    }
}
