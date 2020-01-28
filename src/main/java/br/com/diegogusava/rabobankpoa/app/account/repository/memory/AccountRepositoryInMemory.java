package br.com.diegogusava.rabobankpoa.app.account.repository.memory;

import br.com.diegogusava.rabobankpoa.app.account.domain.Account;
import br.com.diegogusava.rabobankpoa.app.account.repository.AccountRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class AccountRepositoryInMemory implements AccountRepository {

    private final Map<String, Account> db = new ConcurrentHashMap<>();

    public AccountRepositoryInMemory() {
    }

    public AccountRepositoryInMemory(final List<Account> accounts) {
        accounts.forEach(a -> db.put(a.getId(), a));
    }

    @Override
    public Optional<Account> findActiveAccountById(String accountId) {
        return Optional.ofNullable(db.get(accountId)).filter(a -> a.getEnded() == null);
    }

    @Override
    public List<Account> findActiveAccountByIds(Set<String> ids) {
        return ids.stream().map(db::get)
                .filter(a -> a != null && a.getEnded() == null)
                .collect(Collectors.toList());
    }

}
