package co.com.dgallego58.usecase;

import co.com.dgallego58.access.RegistryAccessUseCaseService;
import co.com.dgallego58.domain.access.model.AuthHandler;
import co.com.dgallego58.domain.access.model.UserRepository;
import co.com.dgallego58.domain.contact.model.ContactRepository;
import co.com.dgallego58.contact.usecase.ContactUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement

public class ConfigUseCase {

    @Bean
    public RegistryAccessUseCaseService registryAccessService(AuthHandler authHandler, UserRepository userRepository) {
        return new RegistryAccessUseCaseService(authHandler, userRepository);
    }

    @Bean
    public ContactUseCase contactUseCase(ContactRepository contactRepository) {
        return new ContactUseCase(contactRepository);
    }
}
