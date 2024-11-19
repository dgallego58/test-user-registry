package co.com.dgallego58.api;

import co.com.dgallego58.access.UserAccessUseCase;
import co.com.dgallego58.api.access.UserRegistry;
import co.com.dgallego58.domain.access.model.User;
import co.com.dgallego58.domain.access.model.UserRegistered;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/access")
@Validated
public class AccessController {

    private static final Logger log = LoggerFactory.getLogger(AccessController.class);
    private final UserAccessUseCase userAccessUseCase;

    public AccessController(UserAccessUseCase userAccessUseCase) {
        this.userAccessUseCase = userAccessUseCase;
    }

    @PostMapping(path = "/registry")
    public ResponseEntity<UserRegistered> registry(@Valid @RequestBody UserRegistry userRegistry) {
        log.info("Registry request received");
        var access = userAccessUseCase.register(userRegistry.toModel());
        return ResponseEntity.ok(access);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<String> authenticate(@RequestBody User user) {
        var token = userAccessUseCase.authenticate(user.username(), user.password());
        return ResponseEntity.ok(token);
    }
}
