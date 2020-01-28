package br.com.diegogusava.rabobankpoa.app.operator.domain;

import lombok.Data;

import java.util.Set;

@Data
public class AccountOperator {

    private String id;
    private String accountId;
    private String operatorId;
    private String operatorGroupId;
    private PermissionDirection direction;
    private Set<AccountPermission> permissions;
    private AccountOperatorStatus status;

}
