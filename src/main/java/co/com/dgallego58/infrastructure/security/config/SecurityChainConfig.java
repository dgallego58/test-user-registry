package co.com.dgallego58.infrastructure.security.config;

import co.com.dgallego58.infrastructure.data.repository.UserRepo;
import co.com.dgallego58.infrastructure.helper.JsonUtil;
import co.com.dgallego58.infrastructure.security.adapter.UserDetailsServiceImpl;
import co.com.dgallego58.infrastructure.security.config.auxiliar.RequestExclusions;
import co.com.dgallego58.infrastructure.security.filter.JwtControl;
import co.com.dgallego58.infrastructure.security.filter.JwtFilter;
import co.com.dgallego58.infrastructure.security.filter.JwtValidation;
import co.com.dgallego58.infrastructure.security.filter.jwt.Sanitizers;
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
@EnableWebSecurity
public class SecurityChainConfig {

    private final JsonUtil jsonUtil;
    private final RequestExclusions requestExclusions;

    public SecurityChainConfig(JsonUtil jsonUtil, RequestExclusions requestExclusions) {
        this.jsonUtil = jsonUtil;
        this.requestExclusions = requestExclusions;
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
            .addFilterBefore(new JwtFilter(jwtValidation()), UsernamePasswordAuthenticationFilter.class)
            .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepo userRepo) {
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

    @Bean
    public JwtValidation jwtValidation() {
        return Sanitizers.safeDecoder();
    }

    @Bean
    public JwtControl jwtControl() {
        return new Sanitizers.Encoder(jsonUtil);
    }

}
