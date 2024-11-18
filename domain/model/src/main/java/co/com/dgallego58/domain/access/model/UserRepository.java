package co.com.dgallego58.domain.access.model;

public interface UserRepository {


    UserRegistered getUser(String username);

    UserRegistered save(UserRegistered user);

}
