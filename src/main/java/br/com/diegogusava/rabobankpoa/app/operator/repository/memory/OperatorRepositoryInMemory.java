package br.com.diegogusava.rabobankpoa.app.operator.repository.memory;

import br.com.diegogusava.rabobankpoa.app.operator.domain.Operator;
import br.com.diegogusava.rabobankpoa.app.operator.repository.OperatorRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class OperatorRepositoryInMemory implements OperatorRepository {

    private final Map<String, Operator> db = new ConcurrentHashMap<>();

    public OperatorRepositoryInMemory() {
    }

    public OperatorRepositoryInMemory(List<Operator> operators) {
        operators.forEach(op -> db.put(op.getId(), op));
    }

    @Override
    public Optional<Operator> findById(String id) {
        return Optional.ofNullable(db.get(id));
    }

}
