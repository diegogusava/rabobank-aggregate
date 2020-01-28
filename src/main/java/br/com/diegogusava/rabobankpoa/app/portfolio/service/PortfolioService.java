package br.com.diegogusava.rabobankpoa.app.portfolio.service;

import br.com.diegogusava.rabobankpoa.app.account.domain.Account;
import br.com.diegogusava.rabobankpoa.app.portfolio.domain.AccountDetail;

import java.util.List;

/**
 * Responsible for aggregate data from other services.
 */
public interface PortfolioService {

    /**
     * Find all accounts that an operator can have access.
     *
     * @param operatorId Operator ID
     * @return List of accounts
     */
    List<Account> findAccountsByOperatorId(final String operatorId);

    /**
     * Find account information and all account cards and creates account overview.
     *
     * @param accountId  Account Id
     * @param operatorId Operator Id
     * @return account overview
     */
    AccountDetail findByAccountIdAndOperatorId(final String accountId, final String operatorId);

}
