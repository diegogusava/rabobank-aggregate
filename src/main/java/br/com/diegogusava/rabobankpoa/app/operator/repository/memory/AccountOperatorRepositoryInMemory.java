package br.com.diegogusava.rabobankpoa.app.operator.repository.memory;

import br.com.diegogusava.rabobankpoa.app.operator.domain.AccountOperator;
import br.com.diegogusava.rabobankpoa.app.operator.domain.AccountOperatorStatus;
import br.com.diegogusava.rabobankpoa.app.operator.domain.AccountPermission;
import br.com.diegogusava.rabobankpoa.app.operator.repository.AccountOperatorRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class AccountOperatorRepositoryInMemory implements AccountOperatorRepository {

    private final Map<String, AccountOperator> db = new ConcurrentHashMap<>();

    public AccountOperatorRepositoryInMemory() {
    }

    public AccountOperatorRepositoryInMemory(List<AccountOperator> accountOperators) {
        accountOperators.forEach(ao -> db.put(ao.getId(), ao));
    }

    @Override
    public Set<String> findActiveAccountIdByOperatorOrGroup(final String operatorId, final Set<String> operatorGroupIds) {
        return db.values().stream()
                .filter(isEqualToOperatorIdOrContainsGroupId(operatorId, operatorGroupIds))
                .filter(a -> a.getStatus() == AccountOperatorStatus.ACTIVE)
                .map(AccountOperator::getAccountId)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> findActiveAccountIdByOperatorOrGroupAndPermission(final String operatorId,
                                                                         final Set<String> operatorGroupIds,
                                                                         final AccountPermission permission) {
        return db.values().stream()
                .filter(isEqualToOperatorIdOrContainsGroupId(operatorId, operatorGroupIds))
                .filter(ao -> ao.getPermissions().contains(permission))
                .filter(a -> a.getStatus() == AccountOperatorStatus.ACTIVE)
                .map(AccountOperator::getAccountId)
                .collect(Collectors.toSet());
    }

    private Predicate<AccountOperator> isEqualToOperatorIdOrContainsGroupId(final String operatorId,
                                                                            final Set<String> operatorGroupIds) {
        return (ao) -> {
            if (ao.getOperatorId() != null && ao.getOperatorId().equals(operatorId)) {
                return true;
            }

            if (ao.getOperatorGroupId() == null || operatorGroupIds == null) {
                return false;
            }

            return operatorGroupIds.contains(ao.getOperatorGroupId());
        };
    }

}
