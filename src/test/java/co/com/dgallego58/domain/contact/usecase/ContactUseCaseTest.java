package co.com.dgallego58.domain.contact.usecase;

import co.com.dgallego58.domain.contact.model.ContactRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


class ContactUseCaseTest {


    ContactUseCase contactUseCase;
    ContactRepository contactRepository;

    @BeforeEach
    void setUp() {
        contactRepository = Mockito.mock(ContactRepository.class);
        contactUseCase = new ContactUseCase(contactRepository);
    }

    @Test
    void findContactsWorks() {

        when(contactRepository.findAllByUser(anyString())).thenReturn(List.of());

        var list = contactUseCase.byUserName("test");
        assertThat(list).isEmpty();
    }
}
