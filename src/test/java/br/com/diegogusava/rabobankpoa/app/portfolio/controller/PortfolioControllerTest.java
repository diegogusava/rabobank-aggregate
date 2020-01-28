package br.com.diegogusava.rabobankpoa.app.portfolio.controller;

import br.com.diegogusava.rabobankpoa.app.account.domain.Account;
import br.com.diegogusava.rabobankpoa.app.auth.repository.UserRepository;
import br.com.diegogusava.rabobankpoa.app.card.domain.*;
import br.com.diegogusava.rabobankpoa.app.portfolio.domain.AccountDetail;
import br.com.diegogusava.rabobankpoa.app.portfolio.service.PortfolioService;
import br.com.diegogusava.rabobankpoa.generated.model.*;
import br.com.diegogusava.rabobankpoa.security.JwtToken;
import br.com.diegogusava.rabobankpoa.security.WebSecurityConfig;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static br.com.diegogusava.rabobankpoa.app.portfolio.controller.PortfolioRoles.ROLE_OPERATOR;
import static br.com.diegogusava.rabobankpoa.security.SecurityConstants.TOKEN_HEADER;
import static br.com.diegogusava.rabobankpoa.security.SecurityConstants.TOKEN_PREFIX;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PortfolioController.class)
@Import(WebSecurityConfig.class)
public class PortfolioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PortfolioService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void findAccounts() throws Exception {
        Account account = new Account();
        account.setId("1");
        account.setName("Personal Account");
        when(service.findAccountsByOperatorId(any())).thenReturn(List.of(account));

        var token = JwtToken.builder().withUsername("super").withOperatorId("1").withRole(ROLE_OPERATOR).build();
        final MockHttpServletRequestBuilder request = get("/accounts").with(getAuthorization(token));

        final MockHttpServletResponse response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        final String json = response.getContentAsString();
        final List<AccountDto> result = objectMapper.readValue(json, new TypeReference<List<AccountDto>>() {
        });

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getId()).isEqualTo("1");
        assertThat(result.get(0).getName()).isEqualTo("Personal Account");
    }

    @Test
    public void findAccountsWithoutRoleForbidden() throws Exception {
        var token = JwtToken.builder().withUsername("super").withOperatorId("1").build();
        final MockHttpServletRequestBuilder request = get("/accounts").with(getAuthorization(token));

        mockMvc.perform(request).andExpect(status().isForbidden());
    }

    @Test
    public void findAccountsForbidden() throws Exception {
        mockMvc.perform(get("/accounts")).andExpect(status().isForbidden());
    }

    @Test
    public void findAccountById() throws Exception {
        final AccountDetail account = createAccountDetail();
        when(service.findByAccountIdAndOperatorId("1", "1")).thenReturn(account);
        var token = JwtToken.builder().withUsername("super").withOperatorId("1").withRole(ROLE_OPERATOR).build();

        final MockHttpServletResponse response = mockMvc.perform(get("/accounts/1").with(getAuthorization(token)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();

        final String json = response.getContentAsString();
        final AccountDetailDto result = objectMapper.readValue(json, AccountDetailDto.class);

        assertAccountDetail(account, result);
        assertThat(result.getCreditCards().size()).isEqualTo(1);
        assertThat(result.getDebitCards().size()).isEqualTo(1);
        assertFirstCreditCard(account, result);
        assertFirstDebitCard(account, result);
    }

    private void assertAccountDetail(final AccountDetail account, final AccountDetailDto result) {
        assertThat(result.getId()).isEqualTo(account.getId());
        assertThat(result.getName()).isEqualTo(account.getName());
        assertThat(result.getAccountHolder()).isEqualTo(account.getAccountHolder());
        assertThat(result.getBalance()).isEqualTo(account.getBalance());
        assertThat(result.getCreatedDate()).isEqualTo(account.getCreatedDate());
    }

    private void assertFirstCreditCard(final AccountDetail account, final AccountDetailDto result) {
        final CreditCard creditCard = account.getCreditCards().get(0);
        final CreditCardDto creditCardDto = result.getCreditCards().get(0);

        assertThat(creditCardDto.getId()).isEqualTo(creditCard.getId());
        assertThat(creditCardDto.getCardHolder()).isEqualTo(creditCard.getCardHolder());
        assertThat(creditCardDto.getCardNumber()).isEqualTo(creditCard.getCardNumber());
        assertThat(creditCardDto.getSequenceNumber()).isEqualTo(creditCard.getSequenceNumber());
        assertThat(creditCardDto.getStatus()).isEqualTo(CardStatusDto.valueOf(creditCard.getStatus().name()));
        assertThat(creditCardDto.getMonthlyLimit()).isEqualTo(creditCard.getMonthlyLimit());
    }

    private void assertFirstDebitCard(final AccountDetail account, final AccountDetailDto dto) {
        final DebitCard debitCard = account.getDebitCards().get(0);
        final DebitCardDto debitCardDto = dto.getDebitCards().get(0);

        assertThat(debitCardDto.getId()).isEqualTo(debitCard.getId());
        assertThat(debitCardDto.getCardHolder()).isEqualTo(debitCard.getCardHolder());
        assertThat(debitCardDto.getCardNumber()).isEqualTo(debitCard.getCardNumber());
        assertThat(debitCardDto.getSequenceNumber()).isEqualTo(debitCard.getSequenceNumber());
        assertThat(debitCardDto.getStatus()).isEqualTo(CardStatusDto.valueOf(debitCard.getStatus().name()));

        assertThat(debitCardDto.getAtmLimit().getAmount()).isEqualTo(debitCard.getAtmLimit().getAmount());
        assertThat(debitCardDto.getAtmLimit().getPeriod().name()).isEqualTo(debitCard.getAtmLimit().getPeriod().name());

        assertThat(debitCardDto.getPosLimit().getAmount()).isEqualTo(debitCard.getPosLimit().getAmount());
        assertThat(debitCardDto.getPosLimit().getPeriod().name()).isEqualTo(debitCard.getPosLimit().getPeriod().name());
    }

    private AccountDetail createAccountDetail() {
        final var accountId = "1010";
        AccountDetail account = new AccountDetail();
        account.setId(accountId);
        account.setName("Personal Account");
        account.setBalance(BigDecimal.TEN);
        account.setAccountHolder("Account Holder");
        account.setCreatedDate(LocalDate.of(2019, 12, 1));

        final DebitCard debitCard = new DebitCard();
        debitCard.setId("1");
        debitCard.setAccountId(accountId);
        debitCard.setStatus(CardStatus.ACTIVE);
        debitCard.setCardHolder("Credit Card Holder");
        debitCard.setCardNumber("121234");
        debitCard.setSequenceNumber("1234");
        debitCard.setContactless(true);
        debitCard.setAtmLimit(new CardLimit(BigDecimal.TEN, PeriodUnit.DAY));
        debitCard.setPosLimit(new CardLimit(BigDecimal.TEN, PeriodUnit.DAY));
        account.setDebitCards(List.of(debitCard));

        final CreditCard creditCard = new CreditCard();
        creditCard.setId("1");
        creditCard.setAccountId(accountId);
        creditCard.setStatus(CardStatus.ACTIVE);
        creditCard.setCardHolder("Credit Card Holder");
        creditCard.setCardNumber("12341234");
        creditCard.setSequenceNumber("123");
        account.setCreditCards(List.of(creditCard));
        return account;
    }

    @Test
    public void findAccountByIdForbidden() throws Exception {
        mockMvc.perform(get("/accounts/1")).andExpect(status().is(403));
    }

    private RequestPostProcessor getAuthorization(String token) {
        return request -> {
            request.addHeader(TOKEN_HEADER, TOKEN_PREFIX + token);
            return request;
        };
    }

}
