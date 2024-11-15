package co.com.dgallego58.infrastructure.data.repository.adapter;

import co.com.dgallego58.domain.contact.model.Contact;
import co.com.dgallego58.domain.contact.model.ContactRepository;
import co.com.dgallego58.infrastructure.data.repository.ContactRepo;
import co.com.dgallego58.infrastructure.data.repository.entity.ContactEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Function;

@Repository
public class ContactAdapter implements ContactRepository {

    private final ContactRepo contactRepo;

    public ContactAdapter(ContactRepo contactRepo) {
        this.contactRepo = contactRepo;
    }

    @Override
    public List<Contact> findAllByUser(String userName) {
        Function<ContactEntity, Contact> mapToUser =
                e -> new Contact(e.getNumber(), e.getCityCode(), e.getCountryCode());
        return contactRepo.findAllByUserUsername(userName).stream()
                          .map(mapToUser)
                          .toList();
    }
}
