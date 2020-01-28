package br.com.diegogusava.rabobankpoa.app.card.repository.memory;

import br.com.diegogusava.rabobankpoa.app.card.domain.CardStatus;
import br.com.diegogusava.rabobankpoa.app.card.domain.DebitCard;
import br.com.diegogusava.rabobankpoa.app.card.repository.DebitCardRepository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class DebitCardRepositoryInMemory implements DebitCardRepository {

    private final Map<String, DebitCard> db = new ConcurrentHashMap<>();

    public DebitCardRepositoryInMemory() {
    }

    public DebitCardRepositoryInMemory(List<DebitCard> cards) {
        cards.forEach(card -> this.db.put(card.getId(), card));
    }

    @Override
    public List<DebitCard> findActiveDebitCardByAccountId(final String accountId) {
        return this.db.values().stream()
                .filter(a -> accountId.equalsIgnoreCase(a.getAccountId()))
                .filter(cc -> cc.getStatus() == CardStatus.ACTIVE)
                .collect(Collectors.toList());
    }
}
