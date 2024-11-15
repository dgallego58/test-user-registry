package co.com.dgallego58.domain.access.model;

import co.com.dgallego58.domain.access.Contact;

import java.util.List;

public interface ContactRepository {

    List<Contact> findAllByUser(String userName);

}
