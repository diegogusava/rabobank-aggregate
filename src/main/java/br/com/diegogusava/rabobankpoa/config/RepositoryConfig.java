package br.com.diegogusava.rabobankpoa.config;

import br.com.diegogusava.rabobankpoa.app.account.repository.AccountRepository;
import br.com.diegogusava.rabobankpoa.app.account.repository.memory.AccountRepositoryInMemory;
import br.com.diegogusava.rabobankpoa.app.auth.repository.UserRepository;
import br.com.diegogusava.rabobankpoa.app.auth.repository.memory.UserRepositoryInMemory;
import br.com.diegogusava.rabobankpoa.app.card.repository.CreditCardRepository;
import br.com.diegogusava.rabobankpoa.app.card.repository.DebitCardRepository;
import br.com.diegogusava.rabobankpoa.app.card.repository.memory.CreditCardRepositoryInMemory;
import br.com.diegogusava.rabobankpoa.app.card.repository.memory.DebitCardRepositoryInMemory;
import br.com.diegogusava.rabobankpoa.app.operator.repository.AccountOperatorRepository;
import br.com.diegogusava.rabobankpoa.app.operator.repository.OperatorRepository;
import br.com.diegogusava.rabobankpoa.app.operator.repository.memory.AccountOperatorRepositoryInMemory;
import br.com.diegogusava.rabobankpoa.app.operator.repository.memory.OperatorRepositoryInMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    @Bean
    public AccountRepository accountRepository() {
        return new AccountRepositoryInMemory(MockData.getAccounts());
    }

    @Bean
    public CreditCardRepository creditCardRepository() {
        return new CreditCardRepositoryInMemory(MockData.getCreditCards());
    }

    @Bean
    public DebitCardRepository debitCardRepository() {
        return new DebitCardRepositoryInMemory(MockData.getDebitCards());
    }

    @Bean
    public AccountOperatorRepository accountOperatorRepository() {
        return new AccountOperatorRepositoryInMemory(MockData.getAccountOperators());
    }

    @Bean
    public OperatorRepository operatorRepository() {
        return new OperatorRepositoryInMemory(MockData.getOperators());
    }

    @Bean
    public UserRepository getUserRepository() {
        return new UserRepositoryInMemory(MockData.getUsers());
    }

}
