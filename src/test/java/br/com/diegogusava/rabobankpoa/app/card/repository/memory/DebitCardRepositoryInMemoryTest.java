package br.com.diegogusava.rabobankpoa.app.card.repository.memory;

import br.com.diegogusava.rabobankpoa.app.card.domain.CardStatus;
import br.com.diegogusava.rabobankpoa.app.card.domain.DebitCard;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class DebitCardRepositoryInMemoryTest {

    @Test
    void findActiveDebitCardByAccountId() {
        var cards = List.of(
                newDebitCard("1", "2020", CardStatus.ACTIVE),
                newDebitCard("2", "1010", CardStatus.ACTIVE),
                newDebitCard("3", "1010", CardStatus.BLOCKED)
        );

        var db = new DebitCardRepositoryInMemory(cards);
        final var result = db.findActiveDebitCardByAccountId("1010")
                .stream().map(DebitCard::getId).collect(Collectors.toList());

        assertThat(result).containsExactlyInAnyOrder("2");

    }

    private DebitCard newDebitCard(String id, String accountId, CardStatus status) {
        var card = new DebitCard();
        card.setId(id);
        card.setAccountId(accountId);
        card.setStatus(status);
        return card;
    }
}