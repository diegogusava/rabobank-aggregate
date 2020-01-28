package br.com.diegogusava.rabobankpoa.app.operator.repository.memory;

import br.com.diegogusava.rabobankpoa.app.operator.domain.Operator;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class OperatorRepositoryInMemoryTest {

    @Test
    void findById() {
        var repository = new OperatorRepositoryInMemory(List.of(
                new Operator("1", "a", null),
                new Operator("2", "a", Set.of("1"))
        ));

        assertThat(repository.findById("1").map(Operator::getId)).isEqualTo(Optional.of("1"));
        assertThat(repository.findById("2").map(Operator::getId)).isEqualTo(Optional.of("2"));
        assertThat(repository.findById("3")).isEqualTo(Optional.empty());
    }
}