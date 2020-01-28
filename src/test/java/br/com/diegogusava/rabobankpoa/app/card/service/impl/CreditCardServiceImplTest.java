package br.com.diegogusava.rabobankpoa.app.card.service.impl;

import br.com.diegogusava.rabobankpoa.app.card.repository.CreditCardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CreditCardServiceImplTest {

    @Test
    void findActiveCreditCardsByAccountId(@Mock CreditCardRepository repository) {
        var service = new CreditCardServiceImpl(repository);

        service.findActiveCreditCardsByAccountId("1");

        verify(repository).findActiveCreditCardByAccountId("1");
    }

}