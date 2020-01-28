package br.com.diegogusava.rabobankpoa.app.operator.service;

import br.com.diegogusava.rabobankpoa.app.operator.domain.AccountPermission;

import java.util.Set;

/**
 * Responsible for the relation among account and operator.
 */
public interface AccountOperatorService {

    /**
     * Find all accounts that the operator has access.
     *
     * @param operatorId Operator Id
     * @return accounts ids
     */
    Set<String> findActiveAccountIdsByOperatorId(final String operatorId);

    /**
     * Verify whether the operator can have access to the account overview.
     *
     * @param permission Permission
     * @param accountId  Account Id
     * @param operatorId Operator Id
     * @return true if the operator has access to the account or false otherwise.
     */
    boolean isAllowedByAccountIdAndOperatorId(final AccountPermission permission, final String accountId, final String operatorId);

}
