package br.com.diegogusava.rabobankpoa.app.operator.repository;

import br.com.diegogusava.rabobankpoa.app.operator.domain.Operator;

import java.util.Optional;

public interface OperatorRepository {

    Optional<Operator> findById(final String id);

}
