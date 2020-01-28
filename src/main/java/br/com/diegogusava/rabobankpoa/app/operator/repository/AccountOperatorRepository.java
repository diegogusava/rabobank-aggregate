package br.com.diegogusava.rabobankpoa.app.operator.repository;

import br.com.diegogusava.rabobankpoa.app.operator.domain.AccountPermission;

import java.util.Set;

public interface AccountOperatorRepository {

    Set<String> findActiveAccountIdByOperatorOrGroup(final String operatorId, final Set<String> operatorGroupId);

    Set<String> findActiveAccountIdByOperatorOrGroupAndPermission(final String operatorId,
                                                                  final Set<String> operatorGroupId,
                                                                  final AccountPermission permission);

}
