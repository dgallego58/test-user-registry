package co.com.dgallego58.security;

import co.com.dgallego58.domain.access.model.UserRepository;
import co.com.dgallego58.domain.utils.JsonUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import co.com.dgallego58.security.adapter.UserDetailsServiceImpl;
import co.com.dgallego58.security.filter.JwtControl;
import co.com.dgallego58.security.filter.JwtValidation;
import co.com.dgallego58.security.filter.jwt.Sanitizers;


@Configuration
public class SecurityBeansConfig {



    @Bean
    public JwtValidation jwtValidation() {
        return Sanitizers.safeDecoder();
    }

    @Bean
    public JwtControl jwtControl(JsonUtil jsonUtil) {
        return new Sanitizers.Encoder(jsonUtil);
    }
}
