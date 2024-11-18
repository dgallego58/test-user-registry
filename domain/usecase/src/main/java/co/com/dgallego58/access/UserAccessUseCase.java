package co.com.dgallego58.access;

import co.com.dgallego58.domain.access.model.UserRegistered;
import co.com.dgallego58.domain.access.model.UserRegistry;

public interface UserAccessUseCase {

    UserRegistered register(UserRegistry user);

    String authenticate(String username, String password);


}
