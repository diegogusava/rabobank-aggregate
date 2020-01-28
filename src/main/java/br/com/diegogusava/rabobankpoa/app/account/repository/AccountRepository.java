package br.com.diegogusava.rabobankpoa.app.account.repository;

import br.com.diegogusava.rabobankpoa.app.account.domain.Account;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AccountRepository {

    Optional<Account> findActiveAccountById(final String accountId);

    List<Account> findActiveAccountByIds(final Set<String> ids);

}
