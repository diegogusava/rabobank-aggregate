package br.com.diegogusava.rabobankpoa.app.card.service;

import br.com.diegogusava.rabobankpoa.app.card.domain.DebitCard;

import java.util.List;

/**
 * Responsible for the debit cards
 */
public interface DebitCardService {

    /**
     * Find all active debit cards that belong to the account
     *
     * @param accountId Account ID
     * @return List of debit cards
     */
    List<DebitCard> findActiveDebitCardsByAccountId(final String accountId);

}
