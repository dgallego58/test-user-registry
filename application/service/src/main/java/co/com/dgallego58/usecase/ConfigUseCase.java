package co.com.dgallego58.usecase;

import co.com.dgallego58.access.RegistryAccessUseCaseService;
import co.com.dgallego58.contact.usecase.ContactUseCase;
import co.com.dgallego58.domain.access.model.AuthHandler;
import co.com.dgallego58.domain.access.model.UserRepository;
import co.com.dgallego58.domain.contact.model.ContactRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.TransactionProxyFactoryBean;

import java.util.Properties;

@Configuration
@EnableTransactionManagement

public class ConfigUseCase {

    @Bean
    public RegistryAccessUseCaseService registryAccessService(PlatformTransactionManager platformTransactionManager,
                                                              AuthHandler authHandler,
                                                              UserRepository userRepository,
                                                              ContactRepository contactRepository) {

        var proxy = new TransactionProxyFactoryBean();

        proxy.setTransactionManager(platformTransactionManager);
        proxy.setTarget(new RegistryAccessUseCaseService(authHandler, userRepository, contactRepository));

        var trxAttributes = new Properties();
        trxAttributes.setProperty("*", "PROPAGATION_REQUIRED");
        proxy.setProxyTargetClass(true);
        proxy.setTransactionAttributes(trxAttributes);
        proxy.afterPropertiesSet();

        return (RegistryAccessUseCaseService) proxy.getObject();

    }

    @Bean
    public ContactUseCase contactUseCase(ContactRepository contactRepository) {
        return new ContactUseCase(contactRepository);
    }
}
