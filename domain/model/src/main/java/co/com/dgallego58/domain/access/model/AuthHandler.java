package co.com.dgallego58.domain.access.model;

public interface AuthHandler {


    String authenticate(String username, String password);

    String encodePassword(String password);
}
