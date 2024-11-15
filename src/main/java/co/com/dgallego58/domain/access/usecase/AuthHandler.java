package co.com.dgallego58.domain.access.usecase;

import co.com.dgallego58.domain.access.model.UserRegistered;
import co.com.dgallego58.domain.access.model.UserRegistry;

public interface AuthHandler {


    String authenticate(String username, String password);

    UserRegistered register(UserRegistry userRegistry);


}
