package co.com.dgallego58.infrastructure.security.filter;

public interface JwtControl {


    String validateThenDecode(String token);

    <T> T decode(String token, Class<T> expectedObj);

}
