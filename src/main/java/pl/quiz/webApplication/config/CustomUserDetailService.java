package pl.quiz.webApplication.config;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.quiz.webApplication.data.DataRepository;
import pl.quiz.webApplication.objects.User;

/**
 * This class tells Spring Security what each field means
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    /**
     * This filed is automatically injected by Spring
     */
    private final DataRepository dataRepository;

    /**
     * This method sets up which filed correspond to what in Spring Security
     * @param username the username identifying the user whose data is required.
     * @return UserDetails
     * @throws UsernameNotFoundException if the username wasn't found
     */
    @NonNull
    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        User user = dataRepository.authenticateUser(username);

        if (user == null) throw new UsernameNotFoundException("User not found: " + username);

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getLogin())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}
