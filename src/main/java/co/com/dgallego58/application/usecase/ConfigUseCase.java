package co.com.dgallego58.application.usecase;

import co.com.dgallego58.domain.access.model.UserRepository;
import co.com.dgallego58.domain.access.usecase.AuthHandler;
import co.com.dgallego58.domain.access.usecase.service.RegistryAccessService;
import co.com.dgallego58.domain.contact.model.ContactRepository;
import co.com.dgallego58.domain.contact.usecase.ContactUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigUseCase {

    @Bean
    public RegistryAccessService registryAccessService(AuthHandler authHandler, UserRepository userRepository) {
        return new RegistryAccessService(authHandler, userRepository);
    }

    @Bean
    public ContactUseCase contactUseCase(ContactRepository contactRepository) {
        return new ContactUseCase(contactRepository);
    }
}
