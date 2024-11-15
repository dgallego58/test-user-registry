package co.com.dgallego58.application.security;

import co.com.dgallego58.infrastructure.security.config.auxiliar.RequestExclusions;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertyLoaderConfig {

    @Bean
    @ConfigurationProperties(prefix = "public-access")
    public RequestExclusions requestExclusions() {
        return new RequestExclusions();
    }
}
