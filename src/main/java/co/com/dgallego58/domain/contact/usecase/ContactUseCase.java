package co.com.dgallego58.domain.access.contact;


import co.com.dgallego58.domain.access.Contact;
import co.com.dgallego58.domain.access.model.ContactRepository;

import java.util.List;

public class ContactUseCase {

    private final ContactRepository contactRepository;

    public ContactUseCase(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> byUserName(String userName) {
        return contactRepository.findAllByUser(userName);
    }
}
