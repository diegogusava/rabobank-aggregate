package br.com.diegogusava.rabobankpoa.app.account.service.impl;

import br.com.diegogusava.rabobankpoa.app.account.domain.Account;
import br.com.diegogusava.rabobankpoa.app.account.repository.AccountRepository;
import br.com.diegogusava.rabobankpoa.app.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public Optional<Account> findActiveAccountById(String accountId) {
        return accountRepository.findActiveAccountById(accountId);
    }

    @Override
    public List<Account> findAllActiveAccountByIds(Set<String> accountIds) {
        return accountRepository.findActiveAccountByIds(accountIds);
    }

}
