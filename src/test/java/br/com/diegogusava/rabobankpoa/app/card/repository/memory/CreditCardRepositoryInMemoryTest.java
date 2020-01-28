package br.com.diegogusava.rabobankpoa.app.card.repository.memory;

import br.com.diegogusava.rabobankpoa.app.card.domain.CardStatus;
import br.com.diegogusava.rabobankpoa.app.card.domain.CreditCard;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class CreditCardRepositoryInMemoryTest {

    @Test
    void findActiveCreditCardByAccountId() {
        var cards = List.of(
                newCreditCard("1", "2020", CardStatus.ACTIVE),
                newCreditCard("2", "1010", CardStatus.ACTIVE),
                newCreditCard("3", "1010", CardStatus.BLOCKED)
        );

        var db = new CreditCardRepositoryInMemory(cards);
        final var result = db.findActiveCreditCardByAccountId("1010")
                .stream().map(CreditCard::getId).collect(Collectors.toList());

        assertThat(result).containsExactlyInAnyOrder("2");

    }

    private CreditCard newCreditCard(String id, String accountId, CardStatus status) {
        var card = new CreditCard();
        card.setId(id);
        card.setAccountId(accountId);
        card.setStatus(status);
        return card;
    }
}