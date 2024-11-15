package co.com.dgallego58.infrastructure.security.adapter;

import co.com.dgallego58.domain.access.model.User;
import co.com.dgallego58.infrastructure.data.repository.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;

    public UserDetailsServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username)
                       .map(userEntity -> new User(userEntity.getUsername(), userEntity.getPassword()))
                       .map(UserDetailsBase::new)
                       .orElseThrow(() -> new UsernameNotFoundException("user: {" + username + "} not found"));
    }


}
