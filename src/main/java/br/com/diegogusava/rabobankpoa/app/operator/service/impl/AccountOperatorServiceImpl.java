package br.com.diegogusava.rabobankpoa.app.operator.service.impl;

import br.com.diegogusava.rabobankpoa.app.operator.domain.AccountPermission;
import br.com.diegogusava.rabobankpoa.app.operator.repository.AccountOperatorRepository;
import br.com.diegogusava.rabobankpoa.app.operator.repository.OperatorRepository;
import br.com.diegogusava.rabobankpoa.app.operator.service.AccountOperatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class AccountOperatorServiceImpl implements AccountOperatorService {

    private final OperatorRepository operatorRepository;
    private final AccountOperatorRepository accountOperatorRepository;

    @Override
    public Set<String> findActiveAccountIdsByOperatorId(final String operatorId) {
        return operatorRepository.findById(operatorId)
                .map(operator -> accountOperatorRepository.findActiveAccountIdByOperatorOrGroup(operator.getId(),
                        operator.getOperatorGroupIds()))
                .orElse(Set.of());
    }

    @Override
    public boolean isAllowedByAccountIdAndOperatorId(final AccountPermission permission,
                                                     final String accountId,
                                                     final String operatorId) {
        return operatorRepository.findById(operatorId)
                .map(operator -> accountOperatorRepository.findActiveAccountIdByOperatorOrGroupAndPermission(
                        operator.getId(), operator.getOperatorGroupIds(), permission))
                .map(accountIds -> accountIds.contains(accountId))
                .orElse(false);
    }

}
