package co.com.dgallego58.domain.contact.model;

import co.com.dgallego58.domain.access.model.UserRegistry;

import java.util.List;

public interface ContactRepository {

    List<Contact> findAllByUser(String userName);

    void save(UserRegistry userRegistry);

}
