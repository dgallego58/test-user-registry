package co.com.dgallego58.security.adapter;

import co.com.dgallego58.domain.access.model.User;
import co.com.dgallego58.domain.access.model.UserRegistered;
import co.com.dgallego58.domain.access.model.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserRegistered registered = userRepository.getUser(username);
        if (registered == null) {
            throw new UsernameNotFoundException(username);
        }
        var usr = new User(registered.getUserRegistry().name(), registered.getUserRegistry().password());
        return new UserDetailsBase(usr);
    }


}
