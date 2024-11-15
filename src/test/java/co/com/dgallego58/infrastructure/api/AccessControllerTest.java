package co.com.dgallego58.infrastructure.api;

import co.com.dgallego58.domain.access.model.UserRegistry;
import co.com.dgallego58.domain.access.usecase.AuthHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AccessController.class)
class AccessControllerTest {

    @SystemStub
    static EnvironmentVariables environmentVariables =
            new EnvironmentVariables();
    @Autowired
    MockMvc mockMvc;
    @MockBean(name = "registryAccessService")
    AuthHandler authHandler;

    @BeforeAll
    static void beforeAll() {
        environmentVariables.set("JWT_SECRET", "k7zgydruikmb46c5oh0ataqgnybscdwh");
    }


    @Test
    void registryWorks() throws Exception {

        ArgumentCaptor<UserRegistry> userRegistryCaptor = ArgumentCaptor.forClass(UserRegistry.class);

        doNothing().when(authHandler).register(userRegistryCaptor.capture());

        mockMvc.perform(MockMvcRequestBuilders.post("/access/registry")
                                              .with(csrf())
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content("""
                                                       {
                                                         "name": "Juan Rodriguez",
                                                         "email": "juan@rodriguez.org",
                                                         "password": "hunter2",
                                                         "phones": [
                                                           {
                                                             "number": "1234567",
                                                             "cityCode": "1",
                                                             "countryCode": "57"
                                                           }
                                                         ]
                                                       }
                                                       """)
               )
               .andExpect(status().isNoContent());

        var reg = userRegistryCaptor.getValue();
        assertThat(reg.name()).isEqualTo("Juan Rodriguez");

    }

    @Test
    void checkLogin() throws Exception {
        ArgumentCaptor<String> userRegistryCaptor = ArgumentCaptor.forClass(String.class);

        when(authHandler.authenticate(userRegistryCaptor.capture(), anyString())).thenReturn("ey...");

        mockMvc.perform(MockMvcRequestBuilders.post("/access/login")
                                              .with(csrf())
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content("""
                                                       {
                                                         "username": "Juan Rodriguez",
                                                         "password": "hunter2"
                                                       }
                                                       """)
               )
               .andExpect(status().isOk());

        var reg = userRegistryCaptor.getValue();
        assertThat(reg).isEqualTo("Juan Rodriguez");
    }
}
