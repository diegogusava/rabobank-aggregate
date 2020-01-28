package br.com.diegogusava.rabobankpoa.app.auth.repository.memory;

import br.com.diegogusava.rabobankpoa.app.auth.domain.User;
import br.com.diegogusava.rabobankpoa.app.auth.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class UserRepositoryInMemory implements UserRepository {

    private Map<String, User> db = new ConcurrentHashMap<>();

    public UserRepositoryInMemory(List<User> users) {
        users.forEach(user -> db.put(user.getUsername(), user));
    }

    @Override
    public List<User> findAllUser() {
        return new ArrayList<>(db.values());
    }

    @Override
    public Optional<User> findByUserName(String username) {
        return Optional.ofNullable(db.get(username));
    }

}
