package co.com.dgallego58.infrastructure.security.adapter;

import co.com.dgallego58.domain.access.model.UserRegistry;
import co.com.dgallego58.domain.access.usecase.AuthHandler;
import co.com.dgallego58.infrastructure.data.repository.ContactRepo;
import co.com.dgallego58.infrastructure.data.repository.UserRepo;
import co.com.dgallego58.infrastructure.data.repository.entity.ContactEntity;
import co.com.dgallego58.infrastructure.data.repository.entity.UserEntity;
import co.com.dgallego58.infrastructure.security.filter.JwtControl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthManagerAdapter implements AuthHandler {

    private final UserRepo userRepo;
    private final ContactRepo contactRepo;

    private final AuthenticationManager authenticationManager;
    private final JwtControl jwtControl;
    private final PasswordEncoder passwordEncoder;


    @Override
    public String authenticate(String username, String password) {
        var authReq = UsernamePasswordAuthenticationToken.unauthenticated(username, password);
        var authResp = authenticationManager.authenticate(authReq);

        return jwtControl.encode(authResp.getPrincipal());
    }

    @Override
    @Transactional
    public void register(UserRegistry userRegistry) {
        var userEntity = new UserEntity();
        userEntity.setUsername(userRegistry.name());
        userEntity.setEmail(userRegistry.email());

        var encoded = passwordEncoder.encode(userRegistry.password());
        userEntity.setPassword(encoded);

        var savedUser = userRepo.save(userEntity);

        userRegistry.phones()
                    .forEach(c -> {
                        var contact = new ContactEntity();
                        contact.setCityCode(c.cityCode());
                        contact.setCountryCode(c.countryCode());
                        contact.setNumber(c.number());

                        contact.setUser(savedUser);

                        contactRepo.save(contact);
                    });


    }
}
