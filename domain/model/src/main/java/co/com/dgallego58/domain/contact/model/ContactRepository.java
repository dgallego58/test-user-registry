package co.com.dgallego58.domain.contact.model;

import java.util.List;

public interface ContactRepository {

    List<Contact> findAllByUser(String userName);

}
