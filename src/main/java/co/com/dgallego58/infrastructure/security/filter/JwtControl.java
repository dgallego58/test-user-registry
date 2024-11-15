package co.com.dgallego58.infrastructure.security.filter;

public interface JwtControl {
    <T> T decodeNoVerifying(String token, Class<T> expectedObj);
String encode(Object payload);
}
