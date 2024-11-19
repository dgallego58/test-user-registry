package co.com.dgallego58.repository.adapter;

import co.com.dgallego58.domain.access.model.UserRegistry;
import co.com.dgallego58.domain.contact.model.Contact;
import co.com.dgallego58.domain.contact.model.ContactRepository;
import co.com.dgallego58.repository.ContactRepo;
import co.com.dgallego58.repository.UserRepo;
import co.com.dgallego58.repository.entity.ContactEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Function;

@Repository
public class ContactAdapter implements ContactRepository {

    private final ContactRepo contactRepo;
    private final UserRepo userRepo;

    public ContactAdapter(ContactRepo contactRepo, UserRepo userRepo) {
        this.contactRepo = contactRepo;
        this.userRepo = userRepo;
    }

    @Override
    public List<Contact> findAllByUser(String userName) {
        Function<ContactEntity, Contact> mapToUser =
                e -> new Contact(e.getNumber(), e.getCityCode(), e.getCountryCode());
        return contactRepo.findAllByUserUsername(userName).stream()
                          .map(mapToUser)
                          .toList();
    }


    @Override
    public void save(UserRegistry registry) {

        var parent = userRepo.findByEmail(registry.email())
                             .orElseThrow(() -> new NoContactParentFound("User not found"));

        Function<Contact, ContactEntity> asEntity = contact -> {
            var e = new ContactEntity();
            e.setCityCode(contact.cityCode());
            e.setCountryCode(contact.countryCode());
            e.setNumber(contact.number());
            e.setUser(parent);
            return e;
        };

        var contactEntities = registry.phones()
                                      .stream()
                                      .map(asEntity)
                                      .toList();

        contactRepo.saveAll(contactEntities);
    }

    public static class NoContactParentFound extends RuntimeException {
        public NoContactParentFound(String message) {
            super(message);
        }
    }
}
