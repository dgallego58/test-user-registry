package co.com.dgallego58.domain.access.security;

import co.com.dgallego58.domain.access.UserRegistry;

public interface AuthHandler {


    String authenticate(String username, String password);

    void register(UserRegistry userRegistry);


}
