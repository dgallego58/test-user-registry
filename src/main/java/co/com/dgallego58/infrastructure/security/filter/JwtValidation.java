package co.com.dgallego58.infrastructure.security.filter;

import java.util.Map;

public interface JwtValidation {


    Map<String, Object> validateThenDecode(String token);


}
