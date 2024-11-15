package co.com.dgallego58.infrastructure.api;

import co.com.dgallego58.domain.access.model.User;
import co.com.dgallego58.domain.access.model.UserRegistry;
import co.com.dgallego58.domain.access.usecase.AuthHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/access")
public class AccessController {

    private final AuthHandler authHandler;

    public AccessController(@Qualifier("registryAccessService") AuthHandler authHandler) {
        this.authHandler = authHandler;
    }
    private static final Logger log = LoggerFactory.getLogger(AccessController.class);

    @PostMapping(path = "/registry")
    public ResponseEntity<Void> registry(@RequestBody UserRegistry userRegistry) {
        log.info("Registry request received");
        authHandler.register(userRegistry);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/login")
    public ResponseEntity<String> authenticate(@RequestBody User user) {
        var token = authHandler.authenticate(user.username(), user.password());
        return ResponseEntity.ok(token);
    }
}
