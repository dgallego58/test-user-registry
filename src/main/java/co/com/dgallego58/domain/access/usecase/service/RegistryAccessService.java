package co.com.dgallego58.domain.access.security.service;

import co.com.dgallego58.domain.access.UserRegistry;
import co.com.dgallego58.domain.access.security.AuthHandler;

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
        System.out.println("registering user");
        authHandler.register(userRegistry);
    }

}
