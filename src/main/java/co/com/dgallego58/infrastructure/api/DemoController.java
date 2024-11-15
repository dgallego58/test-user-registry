package co.com.dgallego58.infrastructure.api;

import co.com.dgallego58.domain.contact.model.Contact;
import co.com.dgallego58.domain.contact.usecase.ContactUseCase;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/demo")
@SecurityRequirement(name = "Bearer Authentication")
public class DemoController {

    private final ContactUseCase contactUseCase;

    public DemoController(ContactUseCase contactUseCase) {
        this.contactUseCase = contactUseCase;
    }

    @GetMapping(path = "/{username}")
    public ResponseEntity<List<Contact>> getContacts(@PathVariable String username) {
        return ResponseEntity.ok(contactUseCase.byUserName(username));
    }
}
