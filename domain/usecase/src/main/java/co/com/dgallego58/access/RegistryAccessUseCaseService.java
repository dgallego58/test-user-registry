package co.com.dgallego58.access;

import co.com.dgallego58.domain.access.model.AuthHandler;
import co.com.dgallego58.domain.access.model.UserRegistered;
import co.com.dgallego58.domain.access.model.UserRegistry;
import co.com.dgallego58.domain.access.model.UserRepository;
import co.com.dgallego58.domain.contact.model.ContactRepository;

import java.time.Instant;

public class RegistryAccessUseCaseService implements UserAccessUseCase {

    public static final String SIMPLE_EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private final AuthHandler authHandler;
    private final UserRepository userRepository;
    private final ContactRepository contactRepository;


    public RegistryAccessUseCaseService(AuthHandler authHandler,
                                        UserRepository userRepository,
                                        ContactRepository contactRepository) {
        this.authHandler = authHandler;
        this.userRepository = userRepository;
        this.contactRepository = contactRepository;
    }

    @Override
    public String authenticate(String username, String password) {
        var token = authHandler.authenticate(username, password);
        var registered = userRepository.getUser(username);
        var updated = registered.toBuilder()
                                .updatedAt(Instant.now())
                                .accessToken(token)
                                .lastLogin(Instant.now())
                                .build();
        userRepository.save(updated);
        return token;
    }

    @Override
    public UserRegistered register(UserRegistry userRegistry) {
        var doesMatch = userRegistry.email().matches(SIMPLE_EMAIL_REGEX);
        if (!doesMatch) {
            throw new UnrecognizableEmailException("mail validation");
        }
        UserRegistered user = userRepository.getUser(userRegistry.name());
        if (user != null) {
            throw new UserAlreadyRegisteredException("username validation");
        }

        var encodedPassword = authHandler.encodePassword(userRegistry.password());

        var safeRegistry = new UserRegistry(userRegistry.name(),
                userRegistry.email(),
                encodedPassword,
                userRegistry.phones());

        var userRegistered = UserRegistered.builder()
                                           .active(true)
                                           .createdAt(Instant.now())
                                           .lastLogin(Instant.now())
                                           .updatedAt(Instant.now())
                                           .userRegistry(safeRegistry)
                                           .build();

        var userSaved = userRepository.save(userRegistered);
        contactRepository.save(userRegistry);

        var token = authHandler.authenticate(userRegistry.name(), userRegistry.password());
        var userWithToken = userSaved.toBuilder().accessToken(token).build();

        return userRepository.save(userWithToken);

    }

    public static class RegistryAccessUseCaseException extends RuntimeException {
        public RegistryAccessUseCaseException(String message) {
            super(message);
        }
    }


    public static class UnrecognizableEmailException extends RegistryAccessUseCaseException {
        public UnrecognizableEmailException(String message) {
            super(message);
        }
    }

    public static class UserAlreadyRegisteredException extends RegistryAccessUseCaseException {
        public UserAlreadyRegisteredException(String message) {
            super(message);
        }
    }


}
