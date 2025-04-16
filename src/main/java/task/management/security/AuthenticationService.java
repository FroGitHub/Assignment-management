package task.management.security;

import lombok.RequiredArgsConstructor;
import task.management.dto.user.UserLoginDto;
import task.management.dto.user.UserResponseLoginDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public UserResponseLoginDto authenticate(UserLoginDto userLoginDto) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginDto.getUsername(),
                        userLoginDto.getPassword())
        );

        String token = jwtUtil.generateToken(authentication.getName());
        return new UserResponseLoginDto(token);
    }
}
