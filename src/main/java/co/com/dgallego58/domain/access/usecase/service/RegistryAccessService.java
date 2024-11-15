package co.com.dgallego58.domain.access.usecase.service;

import co.com.dgallego58.domain.access.model.UserRegistry;
import co.com.dgallego58.domain.access.usecase.AuthHandler;

public class RegistryAccessService implements AuthHandler {

    private final AuthHandler authHandler;

    public RegistryAccessService(AuthHandler authHandler) {
        this.authHandler = authHandler;
    }

    @Override
    public String authenticate(String username, String password) {
        return authHandler.authenticate(username, password);
    }

    @Override
    public void register(UserRegistry userRegistry) {
        authHandler.register(userRegistry);
    }

}
