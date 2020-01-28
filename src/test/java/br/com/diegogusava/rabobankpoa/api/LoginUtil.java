package br.com.diegogusava.rabobankpoa.api;

import br.com.diegogusava.rabobankpoa.security.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
public class LoginUtil {

    @Autowired
    private MockMvc mockMvc;

    public String login(String username, String password) {
        try {
            final MockHttpServletResponse response = mockMvc.perform(
                    post("/login").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .param("username", username)
                            .param("password", password)
            ).andExpect(status().isOk()).andReturn().getResponse();
            return response.getHeader(HttpHeaders.AUTHORIZATION).replace(SecurityConstants.TOKEN_PREFIX, "");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
