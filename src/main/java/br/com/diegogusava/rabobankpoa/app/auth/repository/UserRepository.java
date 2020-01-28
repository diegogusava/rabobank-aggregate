package br.com.diegogusava.rabobankpoa.app.auth.repository;

import br.com.diegogusava.rabobankpoa.app.auth.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAllUser();

    Optional<User> findByUserName(String username);
}
