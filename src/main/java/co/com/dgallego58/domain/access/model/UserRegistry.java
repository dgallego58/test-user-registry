package co.com.dgallego58.domain.access;

import java.util.ArrayList;
import java.util.List;

public record UserRegistry(String name, String email, String password, List<Contact> phones) {

    public UserRegistry(String name, String email, String password, List<Contact> phones) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phones = phones == null ? new ArrayList<>() : phones;
    }
}
