package co.com.dgallego58.domain.contact.usecase;


import co.com.dgallego58.domain.contact.model.Contact;
import co.com.dgallego58.domain.contact.model.ContactRepository;

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
