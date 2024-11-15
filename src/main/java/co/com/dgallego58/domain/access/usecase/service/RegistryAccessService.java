package co.com.dgallego58.domain.access.usecase.service;

import co.com.dgallego58.domain.access.model.UserRegistered;
import co.com.dgallego58.domain.access.model.UserRegistry;
import co.com.dgallego58.domain.access.model.UserRepository;
import co.com.dgallego58.domain.access.usecase.AuthHandler;

import java.time.Instant;

public class RegistryAccessService implements AuthHandler {

    private final AuthHandler authHandler;
    private final UserRepository userRepository;

    public RegistryAccessService(AuthHandler authHandler, UserRepository userRepository) {
        this.authHandler = authHandler;
        this.userRepository = userRepository;
    }

    @Override
    public String authenticate(String username, String password) {
        var token = authHandler.authenticate(username, password);
        var registered = userRepository.getUser(username);
        var updated = registered.toBuilder()
                                .updatedAt(Instant.now())
                                .accessToken(token)
                                .lastLogin(Instant.now())
                                .build();
        userRepository.save(updated);
        return token;

    }

    @Override
    public UserRegistered register(UserRegistry userRegistry) {
        var registered = authHandler.register(userRegistry);
        var token = authHandler.authenticate(userRegistry.name(), userRegistry.password());
        var updated = registered.toBuilder().accessToken(token).active(true).build();
        return userRepository.save(updated);

    }

}
