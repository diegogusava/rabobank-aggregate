package br.com.diegogusava.rabobankpoa.app.account.repository.memory;

import br.com.diegogusava.rabobankpoa.app.account.domain.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class AccountRepositoryInMemoryTest {

    private AccountRepositoryInMemory repository;

    @BeforeEach
    void setUp() {
        var accounts = List.of(
                newAccount("1010", null),
                newAccount("1111", LocalDate.now()),
                newAccount("1212", null)
        );

        this.repository = new AccountRepositoryInMemory(accounts);
    }

    @Test
    void findActiveAccountById() {
        assertThat(this.repository.findActiveAccountById("1010").map(Account::getId).get()).isEqualTo("1010");
        assertThat(this.repository.findActiveAccountById("1111")).isEqualTo(Optional.empty());
    }

    @Test
    void findActiveAccountByIds() {
        final List<String> activeAccounts = this.repository.findActiveAccountByIds(Set.of("1010", "1111", "1212"))
                .stream().map(Account::getId).collect(Collectors.toList());

        assertThat(activeAccounts).containsExactlyInAnyOrder("1010", "1212");
    }

    private Account newAccount(final String id, final LocalDate ended) {
        var account = new Account();
        account.setId(id);
        account.setEnded(ended);
        return account;
    }
}