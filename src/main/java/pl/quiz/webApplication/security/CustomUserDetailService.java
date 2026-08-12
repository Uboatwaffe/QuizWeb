package pl.quiz.webApplication.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.quiz.webApplication.data.DataRepository;
import pl.quiz.webApplication.objects.User;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final DataRepository dataRepository;

    public CustomUserDetailService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;


    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = dataRepository.authenticateUser(username);

        if (user == null) throw new UsernameNotFoundException("User not found: " + username);

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getLogin())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}
