package br.com.diegogusava.rabobankpoa.app.card.repository;

import br.com.diegogusava.rabobankpoa.app.card.domain.CreditCard;

import java.util.List;

public interface CreditCardRepository {

    List<CreditCard> findActiveCreditCardByAccountId(final String accountId);

}
