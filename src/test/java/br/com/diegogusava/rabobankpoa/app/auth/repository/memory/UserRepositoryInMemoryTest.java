package br.com.diegogusava.rabobankpoa.app.auth.repository.memory;

import br.com.diegogusava.rabobankpoa.app.auth.domain.User;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryInMemoryTest {

    private UserRepositoryInMemory repository;

    @Test
    void findAllUser() {
        repository = new UserRepositoryInMemory(List.of(createUser("1", "username 1"),
                createUser("2", "username 2")));
        assertThat(repository.findAllUser().stream().map(User::getId).collect(Collectors.toList()))
                .containsExactlyInAnyOrder("1", "2");
    }

    @Test
    void findByUserName() {
        repository = new UserRepositoryInMemory(List.of(createUser("1", "username 1"),
                createUser("2", "username 2")));
        assertThat(repository.findByUserName("username 1").map(User::getId)).isEqualTo(Optional.of("1"));
    }

    public User createUser(final String id, final String username) {
        final User user = new User();
        user.setId(id);
        user.setUsername(username);
        return user;
    }
}