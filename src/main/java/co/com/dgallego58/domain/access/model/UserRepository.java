package co.com.dgallego58.domain.access.model;

import java.time.Instant;

public interface UserRepository {


    UserRegistered getUser(String username);

    UserRegistered save(UserRegistered user);

}
