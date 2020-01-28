package br.com.diegogusava.rabobankpoa.api.portfolio;

import br.com.diegogusava.rabobankpoa.api.LoginUtil;
import br.com.diegogusava.rabobankpoa.generated.client.ApiClient;
import br.com.diegogusava.rabobankpoa.generated.client.api.PortfolioApi;
import br.com.diegogusava.rabobankpoa.generated.model.AccountDetailDto;
import br.com.diegogusava.rabobankpoa.generated.model.AccountDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.ServletContext;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PortfolioControllerIt {

    @LocalServerPort
    private int randomPort;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private LoginUtil loginUtil;

    @Test
    void findAccounts() {
        final List<String> accounts = getPortfolioApi().findAccounts()
                .stream().map(AccountDto::getId).collect(Collectors.toList());
        assertThat(accounts).containsExactlyInAnyOrder("343434343", "123123123");
    }

    @Test
    void findAccountById() {
        final AccountDetailDto detail = getPortfolioApi().findAccountById("123123123");
        assertThat(detail.getId()).isEqualTo("123123123");
    }

    @Test
    void findAccountByIdAccessDenied() {
        final HttpClientErrorException exp = assertThrows(HttpClientErrorException.class, () ->
                getPortfolioApi().findAccountById("123456789"));
        assertThat(exp.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    PortfolioApi getPortfolioApi() {
        var api = new PortfolioApi();
        final ApiClient apiClient = new ApiClient();
        apiClient.setBearerToken(loginUtil.login("super", "super"));
        apiClient.setBasePath("http://localhost:" + randomPort + servletContext.getContextPath());
        api.setApiClient(apiClient);
        return api;
    }

}