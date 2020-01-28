package br.com.diegogusava.rabobankpoa.app.card.repository.memory;

import br.com.diegogusava.rabobankpoa.app.card.domain.CardStatus;
import br.com.diegogusava.rabobankpoa.app.card.domain.CreditCard;
import br.com.diegogusava.rabobankpoa.app.card.repository.CreditCardRepository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class CreditCardRepositoryInMemory implements CreditCardRepository {

    private final Map<String, CreditCard> db = new ConcurrentHashMap<>();

    public CreditCardRepositoryInMemory() {
    }

    public CreditCardRepositoryInMemory(List<CreditCard> cards) {
        cards.forEach(card -> this.db.put(card.getId(), card));
    }

    @Override
    public List<CreditCard> findActiveCreditCardByAccountId(final String accountId) {
        return this.db.values().stream()
                .filter(a -> a.getAccountId().equalsIgnoreCase(accountId))
                .filter(cc -> cc.getStatus() == CardStatus.ACTIVE)
                .collect(Collectors.toList());
    }
}
