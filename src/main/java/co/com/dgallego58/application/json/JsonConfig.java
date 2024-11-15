package co.com.dgallego58.application.json;

import co.com.dgallego58.infrastructure.helper.jackson.JacksonJsonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonConfig {

    @Bean
    public JacksonJsonUtil getJsonUtil(ObjectMapper objectMapper) {
        return new JacksonJsonUtil(objectMapper);
    }
}
