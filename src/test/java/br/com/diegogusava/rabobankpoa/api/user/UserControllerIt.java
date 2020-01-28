package br.com.diegogusava.rabobankpoa.api.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIt {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void login() throws Exception {
        final MockHttpServletResponse response = mockMvc.perform(
                post("/login").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "super")
                        .param("password", "super")
        ).andExpect(status().isOk()).andReturn().getResponse();
        assertThat(response.getHeader(HttpHeaders.AUTHORIZATION)).isNotEmpty();
    }

    @Test
    void loginUnauthorized() throws Exception {
        mockMvc.perform(
                post("/login").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "user")
                        .param("password", "password1")
        ).andExpect(status().isUnauthorized());
    }

}
