package br.com.diegogusava.rabobankpoa.app.portfolio.service.impl;

import br.com.diegogusava.rabobankpoa.app.account.domain.Account;
import br.com.diegogusava.rabobankpoa.app.account.service.AccountService;
import br.com.diegogusava.rabobankpoa.app.card.domain.CreditCard;
import br.com.diegogusava.rabobankpoa.app.card.domain.DebitCard;
import br.com.diegogusava.rabobankpoa.app.card.service.CreditCardService;
import br.com.diegogusava.rabobankpoa.app.card.service.DebitCardService;
import br.com.diegogusava.rabobankpoa.app.operator.domain.AccountPermission;
import br.com.diegogusava.rabobankpoa.app.operator.service.AccountOperatorService;
import br.com.diegogusava.rabobankpoa.app.portfolio.domain.AccountDetail;
import br.com.diegogusava.rabobankpoa.app.portfolio.exception.AccountAccessDeniedException;
import br.com.diegogusava.rabobankpoa.app.portfolio.exception.AccountNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PortfolioServiceImplTest {

    public static final String OPERATOR_ID = "1";
    public static final String ACCOUNT_ID = "1010";

    private PortfolioServiceImpl service;
    private AccountService accountService;
    private AccountOperatorService accountOperatorService;
    private DebitCardService debitCardService;
    private CreditCardService creditCardService;

    @BeforeEach
    void setUp(@Mock AccountService accountService,
               @Mock AccountOperatorService accountOperatorService,
               @Mock DebitCardService debitCardService,
               @Mock CreditCardService creditCardService) {
        this.accountService = accountService;
        this.accountOperatorService = accountOperatorService;
        this.debitCardService = debitCardService;
        this.creditCardService = creditCardService;
        this.service = new PortfolioServiceImpl(accountService, accountOperatorService, debitCardService,
                creditCardService);
    }

    @Test
    void findAccountsByOperatorId() {
        final Account account = getAccount(ACCOUNT_ID);
        when(accountOperatorService.findActiveAccountIdsByOperatorId(OPERATOR_ID)).thenReturn(Set.of(ACCOUNT_ID));
        when(accountService.findAllActiveAccountByIds(Set.of(ACCOUNT_ID))).thenReturn(List.of(account));

        final List<Account> accounts = service.findAccountsByOperatorId(OPERATOR_ID);
        assertThat(accounts.size()).isEqualTo(1);

        final Account accountResult = accounts.get(0);
        assertThat(accountResult).isEqualTo(account);
    }

    @Test
    void findByAccountIdAndOperatorId() {
        final var cc1 = new CreditCard();
        final var dc1 = new DebitCard();
        final Account account = getAccount(ACCOUNT_ID);
        when(accountOperatorService.isAllowedByAccountIdAndOperatorId(AccountPermission.VIEW, ACCOUNT_ID, OPERATOR_ID))
                .thenReturn(true);
        when(accountService.findActiveAccountById(ACCOUNT_ID)).thenReturn(Optional.of(account));

        when(creditCardService.findActiveCreditCardsByAccountId(ACCOUNT_ID)).thenReturn(List.of(cc1));
        when(debitCardService.findActiveDebitCardsByAccountId(ACCOUNT_ID)).thenReturn(List.of(dc1));

        final AccountDetail detail = service.findByAccountIdAndOperatorId(ACCOUNT_ID, OPERATOR_ID);

        assertThat(detail.getId()).isEqualTo(ACCOUNT_ID);
        assertThat(detail.getName()).isEqualTo(account.getName());
        assertThat(detail.getAccountHolder()).isEqualTo(account.getAccountHolder());
        assertThat(detail.getBalance()).isEqualTo(account.getBalance());
        assertThat(detail.getCreatedDate()).isEqualTo(account.getCreated());
        assertThat(detail.getCreditCards()).containsExactlyInAnyOrder(cc1);
        assertThat(detail.getDebitCards()).containsExactlyInAnyOrder(dc1);
    }

    @Test
    void findByAccountIdAndOperatorIdAccessDenied() {
        when(accountOperatorService.isAllowedByAccountIdAndOperatorId(AccountPermission.VIEW, ACCOUNT_ID, OPERATOR_ID))
                .thenReturn(false);

        assertThrows(AccountAccessDeniedException.class, () ->
                service.findByAccountIdAndOperatorId(ACCOUNT_ID, OPERATOR_ID));
    }

    @Test
    void findByAccountIdAndOperatorIdAccountNotFound() {
        when(accountOperatorService.isAllowedByAccountIdAndOperatorId(AccountPermission.VIEW, ACCOUNT_ID, OPERATOR_ID))
                .thenReturn(true);
        when(accountService.findActiveAccountById(ACCOUNT_ID)).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class, () -> service.findByAccountIdAndOperatorId(ACCOUNT_ID, OPERATOR_ID));
    }

    private Account getAccount(final String accountId) {
        var account = new Account();
        account.setId(accountId);
        account.setName("Account 1");
        account.setCreated(LocalDate.of(2018, 12, 1));
        account.setAccountHolder("Account Holder");
        account.setBalance(BigDecimal.TEN);
        return account;
    }

}