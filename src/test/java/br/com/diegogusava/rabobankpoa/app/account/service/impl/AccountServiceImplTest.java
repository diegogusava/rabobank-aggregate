package br.com.diegogusava.rabobankpoa.app.account.service.impl;

import br.com.diegogusava.rabobankpoa.app.account.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    private AccountServiceImpl service;
    private AccountRepository repository;

    @BeforeEach
    void setUp(@Mock AccountRepository repository) {
        this.service = new AccountServiceImpl(repository);
        this.repository = repository;
    }

    @Test
    void findActiveAccountById() {
        this.service.findActiveAccountById("1");

        verify(repository).findActiveAccountById("1");
    }

    @Test
    void findAllActiveAccountByIds() {
        final Set<String> ids = Set.of("1", "2");
        this.service.findAllActiveAccountByIds(ids);

        verify(repository).findActiveAccountByIds(ids);
    }
}