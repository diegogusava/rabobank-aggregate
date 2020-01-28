package br.com.diegogusava.rabobankpoa.app.account.domain;

import br.com.diegogusava.rabobankpoa.app.operator.domain.AccountDirection;
import br.com.diegogusava.rabobankpoa.app.operator.domain.AccountPermission;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the contract among the account holder and the agent(s) that
 * can have access to the account. Agent can be a person (operatorId)
 * or a group of people (operatorGroupId)
 */
@Data
public class PowerOfAttorney {

    private String id;
    private String grantee;
    private String operatorId;
    private String operatorGroupId;

    private AccountDirection direction;
    private List<AccountPermission> authorizations = new ArrayList<>();

    private List<String> creditCards = new ArrayList<>();
    private List<String> debitCards = new ArrayList<>();

}
