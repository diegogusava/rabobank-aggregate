package br.com.diegogusava.rabobankpoa.app.operator.repository.memory;

import br.com.diegogusava.rabobankpoa.app.operator.domain.AccountOperator;
import br.com.diegogusava.rabobankpoa.app.operator.domain.AccountOperatorStatus;
import br.com.diegogusava.rabobankpoa.app.operator.domain.AccountPermission;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class AccountOperatorRepositoryInMemoryTest {

    private AccountOperatorRepositoryInMemory repository;

    @BeforeEach
    void setUp() {
        repository = new AccountOperatorRepositoryInMemory(List.of(
                newAO("1", "1010", "1", null, AccountOperatorStatus.ACTIVE,
                        Set.of(AccountPermission.VIEW)),
                newAO("2", "2020", "1", null, AccountOperatorStatus.INACTIVE,
                        Set.of(AccountPermission.VIEW)),
                newAO("3", "3030", null, "1", AccountOperatorStatus.ACTIVE,
                        Set.of(AccountPermission.PAYMENT)),
                newAO("4", "4040", null, "1", AccountOperatorStatus.INACTIVE,
                        Set.of(AccountPermission.VIEW))
        ));
    }

    @Test
    void findByOperatorOrGroup() {
        assertThat(repository.findActiveAccountIdByOperatorOrGroup("1", Set.of("1")))
                .containsExactlyInAnyOrder("1010", "3030");

        assertThat(repository.findActiveAccountIdByOperatorOrGroup("2", Set.of("1")))
                .containsExactlyInAnyOrder("3030");

        assertThat(repository.findActiveAccountIdByOperatorOrGroup("3", Set.of("3"))).isEmpty();
        assertThat(repository.findActiveAccountIdByOperatorOrGroup(null, null)).isEmpty();
    }

    @Test
    void findByOperatorOrGroupAndPermission() {
        assertThat(repository.findActiveAccountIdByOperatorOrGroupAndPermission("1", Set.of("1"), AccountPermission.VIEW))
                .containsExactlyInAnyOrder("1010");

        assertThat(repository.findActiveAccountIdByOperatorOrGroupAndPermission("1", Set.of("1"), AccountPermission.PAYMENT))
                .containsExactlyInAnyOrder("3030");

        assertThat(repository.findActiveAccountIdByOperatorOrGroupAndPermission("1", Set.of("1"), AccountPermission.DEBIT_CARD))
                .isEmpty();
    }


    private AccountOperator newAO(final String id,
                                  final String accountId,
                                  final String operatorId,
                                  final String operatorGroupId,
                                  final AccountOperatorStatus status,
                                  final Set<AccountPermission> permissions) {
        var op = new AccountOperator();
        op.setId(id);
        op.setAccountId(accountId);
        op.setOperatorId(operatorId);
        op.setOperatorGroupId(operatorGroupId);
        op.setStatus(status);
        op.setPermissions(permissions);
        return op;
    }

}