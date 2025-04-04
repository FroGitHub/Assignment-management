package ma.student.task.management.security;

import lombok.RequiredArgsConstructor;
import ma.student.task.management.exception.EntityNotFoundException;
import ma.student.task.management.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can`t find user by email: " + username
                ));
    }
}
