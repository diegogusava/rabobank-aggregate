package br.com.diegogusava.rabobankpoa.app.card.service;

import br.com.diegogusava.rabobankpoa.app.card.domain.CreditCard;

import java.util.List;

/**
 * Responsible for credit cards
 */
public interface CreditCardService {

    /**
     * Find all active credit cards that belong to the account
     *
     * @param accountId Account ID
     * @return List of credit cards
     */
    List<CreditCard> findActiveCreditCardsByAccountId(final String accountId);

}
