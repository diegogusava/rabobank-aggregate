package br.com.diegogusava.rabobankpoa.app.operator.service.impl;

import br.com.diegogusava.rabobankpoa.app.operator.domain.Operator;
import br.com.diegogusava.rabobankpoa.app.operator.repository.AccountOperatorRepository;
import br.com.diegogusava.rabobankpoa.app.operator.repository.OperatorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static br.com.diegogusava.rabobankpoa.app.operator.domain.AccountPermission.VIEW;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountOperatorServiceTest {

    private AccountOperatorServiceImpl accountOperatorService;
    private AccountOperatorRepository accountOperatorRepository;
    private OperatorRepository operatorRepository;

    @BeforeEach
    void setUp(@Mock OperatorRepository operatorRepository, @Mock AccountOperatorRepository accountOperatorRepository) {
        this.accountOperatorService = new AccountOperatorServiceImpl(operatorRepository, accountOperatorRepository);
        this.operatorRepository = operatorRepository;
        this.accountOperatorRepository = accountOperatorRepository;
    }

    @Test
    void findAccountsByOperatorId() {
        var operator = getOperator();
        when(operatorRepository.findById("1")).thenReturn(Optional.of(operator));
        when(accountOperatorRepository.findActiveAccountIdByOperatorOrGroup("1", Set.of("1")))
                .thenReturn(Set.of("0101"));

        assertThat(this.accountOperatorService.findActiveAccountIdsByOperatorId("1")).containsExactlyInAnyOrder("0101");
    }

    @Test
    void findAccountsByUnknownOperatorId() {
        when(operatorRepository.findById("1")).thenReturn(Optional.empty());
        assertThat(this.accountOperatorService.findActiveAccountIdsByOperatorId("1")).isEmpty();
    }

    @Test
    void findAccountsByOperatorWithNoAccounts() {
        when(operatorRepository.findById("1")).thenReturn(Optional.of(getOperator()));
        when(accountOperatorRepository.findActiveAccountIdByOperatorOrGroup("1", Set.of("1")))
                .thenReturn(Set.of());

        assertThat(this.accountOperatorService.findActiveAccountIdsByOperatorId("1")).isEmpty();
    }

    @Test
    void isAllowedByAccountIdAndOperatorId() {
        when(operatorRepository.findById("1")).thenReturn(Optional.of(getOperator()));
        when(accountOperatorRepository.findActiveAccountIdByOperatorOrGroupAndPermission("1", Set.of("1"), VIEW))
                .thenReturn(Set.of("1010"));

        assertThat(this.accountOperatorService.isAllowedByAccountIdAndOperatorId(VIEW, "1010", "1")).isTrue();
    }

    @Test
    void isAllowedByUnknownAccount() {
        when(operatorRepository.findById("1")).thenReturn(Optional.of(getOperator()));
        when(accountOperatorRepository.findActiveAccountIdByOperatorOrGroupAndPermission("1", Set.of("1"), VIEW))
                .thenReturn(Set.of());

        assertThat(this.accountOperatorService.isAllowedByAccountIdAndOperatorId(VIEW, "1010", "1")).isFalse();
    }

    @Test
    void isAllowedByUnknownOperator() {
        when(operatorRepository.findById("1")).thenReturn(Optional.empty());
        assertThat(this.accountOperatorService.isAllowedByAccountIdAndOperatorId(VIEW, "1010", "1")).isFalse();
    }

    private Operator getOperator() {
        var operator = new Operator();
        operator.setId("1");
        operator.setOperatorGroupIds(Set.of("1"));
        return operator;
    }
}