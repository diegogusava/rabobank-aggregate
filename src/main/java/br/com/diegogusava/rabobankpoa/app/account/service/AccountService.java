package br.com.diegogusava.rabobankpoa.app.account.service;

import br.com.diegogusava.rabobankpoa.app.account.domain.Account;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Responsible for the account data
 */
public interface AccountService {

    /**
     * Find active account by id
     *
     * @param accountId Account ID
     * @return Account if it exists or empty otherwise.
     */
    Optional<Account> findActiveAccountById(final String accountId);

    /**
     * Find all active accounts by ids.
     *
     * @param accountIds Account ids
     * @return List accounts
     */
    List<Account> findAllActiveAccountByIds(Set<String> accountIds);

}
