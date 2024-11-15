package co.com.dgallego58.domain.access.model;

import co.com.dgallego58.domain.contact.model.Contact;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

import java.util.ArrayList;
import java.util.List;

public record UserRegistry(String name, @Email String email,
                           @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$") String password,
                           List<Contact> phones) {

    public UserRegistry(String name, String email, String password, List<Contact> phones) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phones = phones == null ? new ArrayList<>() : phones;
    }
}
