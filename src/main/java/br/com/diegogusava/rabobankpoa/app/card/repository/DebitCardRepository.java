package br.com.diegogusava.rabobankpoa.app.card.repository;

import br.com.diegogusava.rabobankpoa.app.card.domain.DebitCard;

import java.util.List;

public interface DebitCardRepository {

    List<DebitCard> findActiveDebitCardByAccountId(final String accountId);

}
