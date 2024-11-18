package co.com.dgallego58.security.config;

import co.com.dgallego58.security.config.auxiliar.RequestExclusions;
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
