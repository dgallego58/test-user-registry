package co.com.dgallego58.security.config;

import co.com.dgallego58.domain.access.model.UserRepository;
import co.com.dgallego58.security.adapter.UserDetailsServiceImpl;
import co.com.dgallego58.security.config.auxiliar.RequestExclusions;
import co.com.dgallego58.security.filter.JwtFilter;
import co.com.dgallego58.security.filter.JwtValidation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = true)
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SecurityChainConfig {

    private final RequestExclusions requestExclusions;
    private final JwtValidation jwtValidation;

    public SecurityChainConfig(RequestExclusions requestExclusions, JwtValidation jwtValidation) {
        this.requestExclusions = requestExclusions;
        this.jwtValidation = jwtValidation;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        var excludedPaths = requestExclusions.getPaths().toArray(new String[0]);

        http.csrf(csrf -> csrf.ignoringRequestMatchers(excludedPaths))
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(excludedPaths).permitAll()
                    .anyRequest().authenticated()
            )
            .addFilterBefore(new JwtFilter(jwtValidation), UsernamePasswordAuthenticationFilter.class)
            .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepo) {
        return new UserDetailsServiceImpl(userRepo);
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


}
