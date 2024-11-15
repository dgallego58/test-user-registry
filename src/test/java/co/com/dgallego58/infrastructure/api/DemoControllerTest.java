package co.com.dgallego58.infrastructure.api;

import co.com.dgallego58.domain.contact.model.Contact;
import co.com.dgallego58.domain.contact.usecase.ContactUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(DemoController.class)
class DemoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ContactUseCase contactUseCase;

    @Test
    void contactWorks() throws Exception {

        when(contactUseCase.byUserName(anyString())).thenReturn(List.of(new Contact("a", "b", "c")));

        mockMvc.perform(MockMvcRequestBuilders.get("/demo/test"))
               .andExpect(status().isOk());
    }
}
