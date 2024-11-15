package co.com.dgallego58;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {
        "spring.jpa.open-in-view=false",
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.datasource.username=sa",
        "spring.datasource.username=test",
        "spring.datasource.driver-class-name=org.h2.Driver"

})
@ExtendWith(SystemStubsExtension.class)
class GlobalIntegrationTest {

    @SystemStub
    static EnvironmentVariables environmentVariables = new EnvironmentVariables();

    MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @BeforeAll
    static void beforeAll() {
        environmentVariables.set("JWT_SECRET", "k7zgydruikmb46c5oh0ataqgnybscdwh");
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                                 //.apply(SecurityMockMvcConfigurers.springSecurity())
                                 .build();
    }

    @Test
    void loginFlow() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders.post("/access/registry")
                                              .with(csrf())
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content("""
                                                       {
                                                         "name": "Juan Rodriguez",
                                                         "email": "juan@rodriguez.org",
                                                         "password": "Hunter234567",
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
               .andExpect(status().is2xxSuccessful());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/access/login")
                                                                    .with(csrf())
                                                                    .contentType(MediaType.APPLICATION_JSON)
                                                                    .content("""
                                                                             {
                                                                               "username": "Juan Rodriguez",
                                                                               "password": "Hunter234567"
                                                                             }
                                                                             """)
                                     )
                                     .andExpect(status().isOk()).andReturn();
        String token = mvcResult.getResponse().getContentAsString();
        assertNotNull(token);

        mockMvc.perform(MockMvcRequestBuilders.get("/demo/Juan%20Rodriguez")
                                              .header("Authorization", "Bearer " + token))
               .andDo(print())
               .andExpect(status().isOk());

    }
}
